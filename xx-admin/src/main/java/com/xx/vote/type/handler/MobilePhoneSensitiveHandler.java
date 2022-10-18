package com.xx.vote.type.handler;

import com.xx.vote.type.SensitiveType;
import com.xx.vote.type.SensitiveTypeHandler;
import org.apache.commons.lang3.StringUtils;

public class MobilePhoneSensitiveHandler implements SensitiveTypeHandler {
  public SensitiveType getSensitiveType() {
    return SensitiveType.MOBILE_PHONE;
  }
  
  public String handle(Object src) {
    if (src == null)
      return null; 
    String value = src.toString();
    return StringUtils.left(value, 3).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(value, 4), StringUtils.length(value), "*"), "***"));
  }
}
