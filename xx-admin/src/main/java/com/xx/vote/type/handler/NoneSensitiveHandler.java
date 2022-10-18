package com.xx.vote.type.handler;

import com.xx.vote.type.SensitiveType;
import com.xx.vote.type.SensitiveTypeHandler;

public class NoneSensitiveHandler implements SensitiveTypeHandler {
  public SensitiveType getSensitiveType() {
    return SensitiveType.NONE;
  }
  
  public String handle(Object src) {
    if (src != null)
      return src.toString(); 
    return null;
  }
}
