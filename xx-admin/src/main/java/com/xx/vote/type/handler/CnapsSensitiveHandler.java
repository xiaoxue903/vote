package com.xx.vote.type.handler;

import com.xx.vote.type.SensitiveType;
import com.xx.vote.type.SensitiveTypeHandler;
import org.apache.commons.lang3.StringUtils;

public class CnapsSensitiveHandler implements SensitiveTypeHandler {
  public SensitiveType getSensitiveType() {
    return SensitiveType.CNAPS_CODE;
  }
  
  public String handle(Object src) {
    if (src == null)
      return null; 
    String snapCard = src.toString();
    return StringUtils.rightPad(StringUtils.left(snapCard, 4), StringUtils.length(snapCard), "*");
  }
}
