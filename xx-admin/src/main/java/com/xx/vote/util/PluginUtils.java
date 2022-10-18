package com.xx.vote.util;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class PluginUtils {
  public static <T> T realTarget(Object target) {
    if (Proxy.isProxyClass(target.getClass())) {
      MetaObject metaObject = SystemMetaObject.forObject(target);
      return realTarget(metaObject.getValue("h.target"));
    } 
    return (T)target;
  }
  
  public static <T> List<List<T>> splitList(List<T> list, int len) {
    if (list == null || list.isEmpty() || len < 1)
      return Collections.emptyList(); 
    List<List<T>> result = new ArrayList<>();
    int size = list.size();
    int count = (size + len - 1) / len;
    for (int i = 0; i < count; i++) {
      List<T> subList = list.subList(i * len, ((i + 1) * len > size) ? size : (len * (i + 1)));
      result.add(subList);
    } 
    return result;
  }
}
