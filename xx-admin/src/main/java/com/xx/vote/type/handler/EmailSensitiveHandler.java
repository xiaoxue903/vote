package com.xx.vote.type.handler;

import com.xx.vote.type.SensitiveType;
import com.xx.vote.type.SensitiveTypeHandler;
import org.apache.commons.lang3.StringUtils;

public class EmailSensitiveHandler implements SensitiveTypeHandler {
  public SensitiveType getSensitiveType() {
    return SensitiveType.EMAIL;
  }
  
  public String handle(Object src) {
    if (src == null)
      return null; 
    String email = src.toString();
    int index = StringUtils.indexOf(email, "@");
    if (index <= 1)
      return email; 
    return StringUtils.rightPad(StringUtils.left(email, 1), index, "*").concat(
        StringUtils.mid(email, index, StringUtils.length(email)));
  }
}
