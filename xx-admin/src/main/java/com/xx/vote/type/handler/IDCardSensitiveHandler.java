package com.xx.vote.type.handler;

import com.xx.vote.type.SensitiveType;
import com.xx.vote.type.SensitiveTypeHandler;
import org.apache.commons.lang3.StringUtils;

public class IDCardSensitiveHandler implements SensitiveTypeHandler {
  public SensitiveType getSensitiveType() {
    return SensitiveType.ID_CARD;
  }
  
  public String handle(Object src) {
    if (src == null)
      return null; 
    String idCard = src.toString();
    return StringUtils.left(idCard, 3).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(idCard, 4), StringUtils.length(idCard), "*"), "***"));
  }
}
