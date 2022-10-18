package com.xx.vote.interceptor;

import com.xx.vote.annotation.EncryptMap;
import com.xx.vote.annotation.EncryptQueryParams;
import com.xx.vote.db.Encrypt;
import org.apache.ibatis.javassist.*;
import org.apache.ibatis.javassist.bytecode.CodeAttribute;
import org.apache.ibatis.javassist.bytecode.LocalVariableAttribute;
import org.apache.ibatis.javassist.bytecode.MethodInfo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Modifier;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Aspect
public class DaoMethodAspect {
  private static final Logger log = LoggerFactory.getLogger(DaoMethodAspect.class);
  
  public static final String hashmap_type = "java.util.HashMap";
  
  public static final String list_type = "java.util.List";
  
  public static final String string_type = "java.lang.String";
  
  private Encrypt encrypt;
  
  public DaoMethodAspect(Encrypt encrypt) {
    this.encrypt = encrypt;
  }
  
  @Pointcut("@annotation(com.xx.vote.annotation.EncryptQueryParams)")
  public void annotationPointcut1() {}
  
  @Pointcut("@annotation(com.xx.vote.annotation.EncryptMap)")
  public void annotationPointcut2() {}
  
  @Around("annotationPointcut2()&& @annotation(encryptMap)")
  public Object afterDaoMethod(ProceedingJoinPoint joinPoint, EncryptMap encryptMap) throws Throwable {
    String[] paramsKey = encryptMap.value();
    MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
    String returnTypeName = methodSignature.getReturnType().getTypeName();
    Object results = (Object)joinPoint.proceed();
    if (returnTypeName.equals("java.util.List")) {
      List<Map> list = (List)results;
      if (list == null || list.size() == 0)
        return results; 
      Object map1 = list.get(0);
      if (map1.getClass().getTypeName().equals("java.util.HashMap")) {
        for (int i = 0; i < list.size(); i++) {
          Map<String, String> item = list.get(i);
          for (int j = 0; j < paramsKey.length; j++) {
            if (item.containsKey(paramsKey[j]))
              item.put(paramsKey[j], this.encrypt.decrypt(item.get(paramsKey[j]))); 
          } 
        } 
        results = (Object)list;
      } 
    } 
    return results;
  }
  
  @Around("annotationPointcut1()&& @annotation(encryptQueryParams)")
  public Object beforeDaoMethod(ProceedingJoinPoint joinPoint, EncryptQueryParams encryptQueryParams) throws Throwable {
    String[] paramsKey = encryptQueryParams.value();
    Object[] args = joinPoint.getArgs();
    MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
    String[] actualParams = (new StandardReflectionParameterNameDiscoverer()).getParameterNames(methodSignature.getMethod());
    if (actualParams != null && actualParams.length > 0)
      for (int k = 0; k < args.length; k++) {
        Object arg = args[k];
        String typeName = arg.getClass().getTypeName();
        if ("java.lang.String".equals(typeName)) {
          for (int i = 0; i < paramsKey.length; i++) {
            if (paramsKey[i].equals(actualParams[k])) {
              args[k] = encryptValue((String)args[k]);
              break;
            } 
          } 
        } else if ("java.util.HashMap".equals(typeName)) {
          arg = arg;
          HashMap<String, String> newMap = (HashMap)((HashMap)arg).clone();
          for (int i = 0; i < paramsKey.length; i++) {
            if (newMap.containsKey(paramsKey[i]))
              newMap.put(paramsKey[i], encryptValue(newMap.get(paramsKey[i]))); 
          } 
          args[k] = newMap;
        } else if (isCustomObject(typeName)) {
          Object newArg = copyObject(arg);
          Field[] fieldInfo = newArg.getClass().getDeclaredFields();
          for (Field field : fieldInfo) {
            for (int i = 0; i < paramsKey.length; i++) {
              if (paramsKey[i].equals(field.getName())) {
                ReflectionUtils.makeAccessible(field);
                Object fieldValue = field.get(newArg);
                String afterValue = encryptValue((String)fieldValue);
                if (afterValue != null)
                  field.set(newArg, afterValue); 
                break;
              } 
            } 
          } 
          args[k] = newArg;
        } 
      }  
    return joinPoint.proceed(args);
  }
  
  private boolean isCustomObject(String typeName) {
    return (!typeName.startsWith("java.lang") && 
      !typeName.startsWith("java.util") && 
      !typeName.startsWith("int") && 
      !typeName.startsWith("double") && 
      !typeName.startsWith("long") && 
      !typeName.startsWith("short") && 
      !typeName.startsWith("byte") && 
      !typeName.startsWith("char") && 
      !typeName.startsWith("float"));
  }
  
  private String encryptValue(String value) {
    if (value == null || value == "")
      return value; 
    return this.encrypt.encrypt(value);
  }
  
  public static Object copyObject(Object obj) throws Exception {
    Class<? extends Object> class1 = (Class)obj.getClass();
    Field[] fields = class1.getDeclaredFields();
    Constructor<? extends Object> constructor = class1.getDeclaredConstructor(new Class[0]);
    Object instance = constructor.newInstance(new Object[0]);
    for (Field f : fields) {
      String fname = f.getName();
      Class<?> type = f.getType();
      String setMethodName = "set" + fname.substring(0, 1).toUpperCase() + fname.substring(1);
      String getMethodName = "get" + fname.substring(0, 1).toUpperCase() + fname.substring(1);
      Method gmthod = class1.getDeclaredMethod(getMethodName, null);
      Object gresult = gmthod.invoke(obj, (Object[])null);
      Method smethod = class1.getDeclaredMethod(setMethodName, new Class[] { gmthod.getReturnType() });
      smethod.invoke(instance, new Object[] { gresult });
    } 
    return instance;
  }
  
  public static List<String> getParameterNames(Method method) {
    Parameter[] parameters = method.getParameters();
    List<String> parameterNames = new ArrayList<>();
    for (Parameter parameter : parameters) {
      if (!parameter.isNamePresent())
        throw new IllegalArgumentException("Parameter names are not present!"); 
      String parameterName = parameter.getName();
      parameterNames.add(parameterName);
    } 
    return parameterNames;
  }
  
  private Map<String, Object> getFieldsName(Class cls, String clazzName, String methodName, Object[] args) throws NotFoundException {
    Map<String, Object> map = new HashMap<>();
    ClassPool pool = ClassPool.getDefault();
    ClassClassPath classPath = new ClassClassPath(cls);
    pool.insertClassPath((ClassPath)classPath);
    CtClass cc = pool.get(clazzName);
    CtMethod cm = cc.getDeclaredMethod(methodName);
    MethodInfo methodInfo = cm.getMethodInfo();
    CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
    LocalVariableAttribute attr = (LocalVariableAttribute)codeAttribute.getAttribute("LocalVariableTable");
    if (attr == null);
    int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
    for (int i = 0; i < (cm.getParameterTypes()).length; i++) {
      map.put(attr.variableName(i + pos), args[i]);
    }
    return map;
  }
}
