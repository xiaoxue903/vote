package com.xx.vote.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.LinkedHashMap;
import java.util.Map;

public class JsonUtils {
  public static Map<String, Object> parseToObjectMap(String jsonStr) {
    return (Map<String, Object>)JSON.parseObject(jsonStr, new TypeReference<LinkedHashMap<String, Object>>() {
        
        },  new com.alibaba.fastjson.parser.Feature[0]);
  }
  
  public static String parseMaptoJSONString(Map<String, Object> params) {
    return JSON.toJSONString(params, new SerializerFeature[] { SerializerFeature.WriteMapNullValue });
  }
}
