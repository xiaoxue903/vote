package com.xx.vote.type.handler;

import com.xx.vote.type.SensitiveType;
import com.xx.vote.type.SensitiveTypeHandler;
import org.apache.commons.lang3.StringUtils;

public class FixedPhoneSensitiveHandler implements SensitiveTypeHandler {
  public SensitiveType getSensitiveType() {
    return SensitiveType.FIXED_PHONE;
  }
  
  public String handle(Object src) {
    if (src == null)
      return null; 
    String fixedPhone = src.toString();
    return StringUtils.left(fixedPhone, 2).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(fixedPhone, 4), StringUtils.length(fixedPhone), "*"), "***"));
  }
}
