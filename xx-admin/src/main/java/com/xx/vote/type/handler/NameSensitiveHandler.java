package com.xx.vote.type.handler;

import com.xx.vote.type.SensitiveType;
import com.xx.vote.type.SensitiveTypeHandler;
import org.apache.commons.lang3.StringUtils;

public class NameSensitiveHandler implements SensitiveTypeHandler {
  public SensitiveType getSensitiveType() {
    return SensitiveType.CHINESE_NAME;
  }
  
  public String handle(Object src) {
    if (src == null)
      return ""; 
    String fullName = src.toString();
    String name = StringUtils.left(fullName, 1);
    return StringUtils.rightPad(name, StringUtils.length(fullName), "*");
  }
}
