package com.xx.vote.type.handler;

import com.xx.vote.type.SensitiveType;
import com.xx.vote.type.SensitiveTypeHandler;
import org.apache.commons.lang3.StringUtils;

public class AddressSensitiveHandler implements SensitiveTypeHandler {
  private static final int RIGHT = 10;
  
  private static final int LEFT = 6;
  
  public SensitiveType getSensitiveType() {
    return SensitiveType.ADDRESS;
  }
  
  public String handle(Object src) {
    if (src == null)
      return null; 
    String address = src.toString();
    int length = StringUtils.length(address);
    if (length > 16)
      return StringUtils.rightPad(StringUtils.left(address, length - 10), length, "*"); 
    if (length <= 6)
      return address; 
    return address.substring(0, 7).concat("*****");
  }
}
