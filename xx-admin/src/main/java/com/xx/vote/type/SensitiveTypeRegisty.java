package com.xx.vote.type;

import com.xx.vote.type.handler.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SensitiveTypeRegisty {
  private static final Map<SensitiveType, SensitiveTypeHandler> HANDLER_REGISTY = new ConcurrentHashMap<>();
  
  static {
    HANDLER_REGISTY.put(SensitiveType.NONE, new NoneSensitiveHandler());
    HANDLER_REGISTY.put(SensitiveType.DEFAUL, new DafaultSensitiveHandler());
    HANDLER_REGISTY.put(SensitiveType.CHINESE_NAME, new NameSensitiveHandler());
    HANDLER_REGISTY.put(SensitiveType.ID_CARD, new IDCardSensitiveHandler());
    HANDLER_REGISTY.put(SensitiveType.MOBILE_PHONE, new MobilePhoneSensitiveHandler());
    HANDLER_REGISTY.put(SensitiveType.ADDRESS, new AddressSensitiveHandler());
    HANDLER_REGISTY.put(SensitiveType.EMAIL, new EmailSensitiveHandler());
    HANDLER_REGISTY.put(SensitiveType.BANK_CARD, new BandCardSensitiveHandler());
    HANDLER_REGISTY.put(SensitiveType.FIXED_PHONE, new FixedPhoneSensitiveHandler());
    HANDLER_REGISTY.put(SensitiveType.CNAPS_CODE, new CnapsSensitiveHandler());
    HANDLER_REGISTY.put(SensitiveType.PAY_SIGN_NO, new PaySignNoSensitiveHandler());
  }
  
  public static void put(SensitiveTypeHandler sensitiveTypeHandler) {
    HANDLER_REGISTY.put(sensitiveTypeHandler.getSensitiveType(), sensitiveTypeHandler);
  }
  
  public static SensitiveTypeHandler get(SensitiveType sensitiveType) {
    SensitiveTypeHandler sensitiveTypeHandler = HANDLER_REGISTY.get(sensitiveType);
    if (sensitiveTypeHandler == null)
      throw new IllegalArgumentException("none sensitiveTypeHandler be found!, type:" + sensitiveType.name()); 
    return sensitiveTypeHandler;
  }
  
  public static boolean alreadyBeSentisived(Object src) {
    return (src == null || src.toString().indexOf("*") > 0);
  }
}
