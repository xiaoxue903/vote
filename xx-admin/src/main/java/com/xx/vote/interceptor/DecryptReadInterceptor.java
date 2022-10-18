package com.xx.vote.interceptor;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.xx.vote.annotation.EncryptField;
import com.xx.vote.annotation.SensitiveBinded;
import com.xx.vote.annotation.SensitiveEncryptEnabled;
import com.xx.vote.db.Encrypt;
import com.xx.vote.type.SensitiveType;
import com.xx.vote.type.SensitiveTypeRegisty;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.*;

@Intercepts({@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})})
public class DecryptReadInterceptor implements Interceptor {
    private static final Logger log = LoggerFactory.getLogger(DecryptReadInterceptor.class);

    private static final String MAPPED_STATEMENT = "mappedStatement";

    private Encrypt encrypt;

    public DecryptReadInterceptor(Encrypt encrypt) {
        Objects.requireNonNull(encrypt, "encrypt should not be null!");
        this.encrypt = encrypt;
    }

    public Object intercept(Invocation invocation) throws Throwable {
        List<Object> results = (List<Object>) invocation.proceed();
        if (results.isEmpty() || results.size() == 0)
            return results;
        ResultSetHandler statementHandler = (ResultSetHandler) PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("mappedStatement");
        ResultMap resultMap = mappedStatement.getResultMaps().isEmpty() ? null : mappedStatement.getResultMaps().get(0);
        Object result0 = results.get(0);
        SensitiveEncryptEnabled sensitiveEncryptEnabled = result0.getClass().<SensitiveEncryptEnabled>getAnnotation(SensitiveEncryptEnabled.class);
        if (sensitiveEncryptEnabled == null || !sensitiveEncryptEnabled.value())
            return results;
        Map<String, EncryptField> sensitiveFieldMap = getSensitiveByResultMap(resultMap);
        Map<String, SensitiveBinded> sensitiveBindedMap = getSensitiveBindedByResultMap(resultMap);
        if (sensitiveBindedMap.isEmpty() && sensitiveFieldMap.isEmpty())
            return results;
        for (Object obj : results) {
            MetaObject objMetaObject = mappedStatement.getConfiguration().newMetaObject(obj);
            for (Map.Entry<String, EncryptField> entry : sensitiveFieldMap.entrySet()) {
                String property = entry.getKey();
                String value = (String) objMetaObject.getValue(property);
                if (value != null) {
                    String decryptValue = this.encrypt.decrypt(value);
                    objMetaObject.setValue(property, decryptValue);
                }
            }
            for (Map.Entry<String, SensitiveBinded> entry : sensitiveBindedMap.entrySet()) {
                String property = entry.getKey();
                SensitiveBinded sensitiveBinded = entry.getValue();
                String bindPropety = sensitiveBinded.bindField();
                SensitiveType sensitiveType = sensitiveBinded.value();
                try {
                    String value = (String) objMetaObject.getValue(bindPropety);
                    String resultValue = SensitiveTypeRegisty.get(sensitiveType).handle(value);
                    objMetaObject.setValue(property, resultValue);
                } catch (Exception exception) {
                }
            }
        }
        return results;
    }

    private Map<String, SensitiveBinded> getSensitiveBindedByResultMap(ResultMap resultMap) {
        if (resultMap == null)
            return new HashMap<>(16);
        Map<String, SensitiveBinded> sensitiveBindedMap = new HashMap<>(16);
        Class<?> clazz = resultMap.getType();
        for (Field field : clazz.getDeclaredFields()) {
            SensitiveBinded sensitiveBinded = field.<SensitiveBinded>getAnnotation(SensitiveBinded.class);
            if (sensitiveBinded != null)
                sensitiveBindedMap.put(field.getName(), sensitiveBinded);
        }
        return sensitiveBindedMap;
    }

    private Map<String, EncryptField> getSensitiveByResultMap(ResultMap resultMap) {
        if (resultMap == null)
            return new HashMap<>(16);
        return getSensitiveByType(resultMap.getType());
    }

    private Map<String, EncryptField> getSensitiveByType(Class<?> clazz) {
        Map<String, EncryptField> sensitiveFieldMap = new HashMap<>(16);
        for (Field field : clazz.getDeclaredFields()) {
            EncryptField sensitiveField = field.<EncryptField>getAnnotation(EncryptField.class);
            if (sensitiveField != null)
                sensitiveFieldMap.put(field.getName(), sensitiveField);
        }
        return sensitiveFieldMap;
    }

    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    public void setProperties(Properties properties) {
    }
}
