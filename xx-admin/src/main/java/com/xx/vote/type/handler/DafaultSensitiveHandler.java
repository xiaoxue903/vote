package com.xx.vote.type.handler;


import com.xx.vote.type.SensitiveType;
import com.xx.vote.type.SensitiveTypeHandler;

public class DafaultSensitiveHandler implements SensitiveTypeHandler {
  private static final int SIZE = 6;
  
  private static final int TWO = 2;
  
  private static final String SYMBOL = "*";
  
  public SensitiveType getSensitiveType() {
    return SensitiveType.DEFAUL;
  }
  
  public String handle(Object src) {
    if (null == src || "".equals(src))
      return null; 
    String value = src.toString();
    int len = value.length();
    int pamaone = len / 2;
    int pamatwo = pamaone - 1;
    int pamathree = len % 2;
    StringBuilder stringBuilder = new StringBuilder();
    if (len <= 2) {
      if (pamathree == 1)
        return "*"; 
      stringBuilder.append("*");
      stringBuilder.append(value.charAt(len - 1));
    } else if (pamatwo <= 0) {
      stringBuilder.append(value.substring(0, 1));
      stringBuilder.append("*");
      stringBuilder.append(value.substring(len - 1, len));
    } else if (pamatwo >= 3 && 7 != len) {
      int pamafive = (len - 6) / 2;
      stringBuilder.append(value.substring(0, pamafive));
      for (int i = 0; i < 6; i++)
        stringBuilder.append("*"); 
      if (ispamaThree(pamathree)) {
        stringBuilder.append(value.substring(len - pamafive, len));
      } else {
        stringBuilder.append(value.substring(len - pamafive + 1, len));
      } 
    } else {
      int pamafour = len - 2;
      stringBuilder.append(value.substring(0, 1));
      for (int i = 0; i < pamafour; i++)
        stringBuilder.append("*"); 
      stringBuilder.append(value.substring(len - 1, len));
    } 
    return stringBuilder.toString();
  }
  
  private boolean ispamaThree(int pamathree) {
    // Byte code:
    //   0: iload_1
    //   1: ifne -> 4
    //   4: iload_1
    //   5: ifeq -> 8
    //   8: iconst_0
    //   9: ireturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #70	-> 0
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	10	0	this	Lcom/zdww/mybatis/plugin/sensitive/type/handler/DafaultSensitiveHandler;
    //   0	10	1	pamathree	I
    return true;
  }
}
