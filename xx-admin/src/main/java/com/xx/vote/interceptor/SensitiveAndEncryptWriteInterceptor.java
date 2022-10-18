package com.xx.vote.interceptor;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.xx.vote.annotation.*;
import com.xx.vote.db.Encrypt;
import com.xx.vote.type.SensitiveType;
import com.xx.vote.type.SensitiveTypeRegisty;
import com.xx.vote.util.JsonUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class SensitiveAndEncryptWriteInterceptor implements Interceptor {
  private static final Logger log = LoggerFactory.getLogger(SensitiveAndEncryptWriteInterceptor.class);
  
  private static final String MAPPEDSTATEMENT = "delegate.mappedStatement";
  
  private static final String BOUND_SQL = "delegate.boundSql";
  
  private Encrypt encrypt;
  
  public SensitiveAndEncryptWriteInterceptor(Encrypt encrypt) {
    Objects.requireNonNull(encrypt, "encrypt should not be null!");
    this.encrypt = encrypt;
  }
  
  public Object intercept(Invocation invocation) throws Throwable {
    StatementHandler statementHandler = (StatementHandler) PluginUtils.realTarget(invocation.getTarget());
    MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
    MappedStatement mappedStatement = (MappedStatement)metaObject.getValue("delegate.mappedStatement");
    SqlCommandType commandType = mappedStatement.getSqlCommandType();
    BoundSql boundSql = (BoundSql)metaObject.getValue("delegate.boundSql");
    Object params = boundSql.getParameterObject();
    if (params instanceof Map)
      return invocation.proceed(); 
    SensitiveEncryptEnabled sensitiveEncryptEnabled = (params != null) ? params.getClass().<SensitiveEncryptEnabled>getAnnotation(SensitiveEncryptEnabled.class) : null;
    if (sensitiveEncryptEnabled != null && sensitiveEncryptEnabled.value())
      handleParameters(mappedStatement.getConfiguration(), boundSql, params, commandType); 
    return invocation.proceed();
  }
  
  private void handleParameters(Configuration configuration, BoundSql boundSql, Object param, SqlCommandType commandType) throws Exception {
    Map<String, Object> newValues = new HashMap<>(16);
    MetaObject metaObject = configuration.newMetaObject(param);
    for (Field field : param.getClass().getDeclaredFields()) {
      if (!Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers())) {
        Object value = metaObject.getValue(field.getName());
        Object newValue = value;
        if (value instanceof CharSequence) {
          newValue = handleEncryptField(field, newValue);
          if (isWriteCommand(commandType) && !SensitiveTypeRegisty.alreadyBeSentisived(newValue)) {
            newValue = handleSensitiveField(field, newValue);
            newValue = handleSensitiveJSONField(field, newValue);
          } 
        } 
        if (value != null && newValue != null && !value.equals(newValue))
          newValues.put(field.getName(), newValue); 
      } 
    } 
    for (Map.Entry<String, Object> entry : newValues.entrySet())
      boundSql.setAdditionalParameter(entry.getKey(), entry.getValue()); 
  }
  
  private boolean isWriteCommand(SqlCommandType commandType) {
    return (SqlCommandType.UPDATE.equals(commandType) || SqlCommandType.INSERT.equals(commandType));
  }
  
  private Object handleEncryptField(Field field, Object value) {
    EncryptField encryptField = field.<EncryptField>getAnnotation(EncryptField.class);
    Object newValue = value;
    if (encryptField != null && value != null)
      newValue = this.encrypt.encrypt(value.toString()); 
    return newValue;
  }
  
  private Object handleSensitiveField(Field field, Object value) {
    SensitiveField sensitiveField = field.<SensitiveField>getAnnotation(SensitiveField.class);
    Object newValue = value;
    if (sensitiveField != null && value != null)
      newValue = SensitiveTypeRegisty.get(sensitiveField.value()).handle(value); 
    return newValue;
  }
  
  private Object handleSensitiveJSONField(Field field, Object value) {
    SensitiveJSONField sensitiveJSONField = field.<SensitiveJSONField>getAnnotation(SensitiveJSONField.class);
    Object newValue = value;
    if (sensitiveJSONField != null && value != null)
      newValue = processJsonField(newValue, sensitiveJSONField); 
    return newValue;
  }
  
  private Object processJsonField(Object newValue, SensitiveJSONField sensitiveJSONField) {
    try {
      Map<String, Object> map = JsonUtils.parseToObjectMap(newValue.toString());
      SensitiveJSONFieldKey[] keys = sensitiveJSONField.sensitivelist();
      for (SensitiveJSONFieldKey jsonFieldKey : keys) {
        String key = jsonFieldKey.key();
        SensitiveType sensitiveType = jsonFieldKey.type();
        Object oldData = map.get(key);
        if (oldData != null) {
          String newData = SensitiveTypeRegisty.get(sensitiveType).handle(oldData);
          map.put(key, newData);
        } 
      } 
      return JsonUtils.parseMaptoJSONString(map);
    } catch (Throwable e) {
      log.error(": {}", e.getMessage(), e);
      return newValue;
    } 
  }
  
  public Object plugin(Object o) {
    return Plugin.wrap(o, this);
  }
  
  public void setProperties(Properties properties) {}
}
