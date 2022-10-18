package com.xx.vote.type.handler;

import com.xx.vote.type.SensitiveType;
import com.xx.vote.type.SensitiveTypeHandler;
import org.apache.commons.lang3.StringUtils;

public class PaySignNoSensitiveHandler implements SensitiveTypeHandler {
  public SensitiveType getSensitiveType() {
    return SensitiveType.PAY_SIGN_NO;
  }
  
  public String handle(Object src) {
    if (src == null)
      return null; 
    String agreementNo = src.toString();
    return StringUtils.left(agreementNo, 6).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(agreementNo, 6), StringUtils.length(agreementNo), "*"), "***"));
  }
}
