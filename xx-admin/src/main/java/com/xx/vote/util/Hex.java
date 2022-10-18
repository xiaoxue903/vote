package com.xx.vote.util;

import org.apache.commons.lang3.StringUtils;

public class Hex {
  private static final String BYTE_CONVERTE = "0123456789ABCDEF";
  
  public static String bytesToHexString(byte[] src) {
    if (src == null || src.length <= 0)
      return null; 
    StringBuilder stringBuilder = new StringBuilder("");
    for (int j : src) {
      int v = j & 0xFF;
      String hv = Integer.toHexString(v);
      if (hv.length() < 2)
        stringBuilder.append(0); 
      stringBuilder.append(hv);
    } 
    return stringBuilder.toString();
  }
  
  public static byte[] hexStringToBytes(String hexString) {
    if (StringUtils.isEmpty(hexString))
      return new byte[0]; 
    hexString = hexString.toUpperCase();
    int length = hexString.length() / 2;
    char[] hexChars = hexString.toCharArray();
    byte[] d = new byte[length];
    for (int i = 0; i < length; i++) {
      int pos = i * 2;
      d[i] = (byte)(charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
    } 
    return d;
  }
  
  private static byte charToByte(char c) {
    return (byte)"0123456789ABCDEF".indexOf(c);
  }
}
