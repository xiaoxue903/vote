package com.xx.vote.util;

import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.util.Base64;

public class AesMysqlUtil {
  private static final int AES_KEY_LENGTH_16 = 16;
  
  private static final int AES_KEY_LENGTH_32 = 32;
  
  private static final String KEY_ALGORITHM = "AES";
  
  private static final String DEFAULT_CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
  
  public static void main(String[] args) throws Exception {
    SecretKeySpec aesKey = getSecretKey("0123456789012345");
    String en = encrypt("18093220803", aesKey);
    System.out.println(en);
    System.out.println(decrypt(en, aesKey));
  }

  public static String encrypt(String content, SecretKeySpec aesKey) throws Exception {
    if (StringUtils.isEmpty(content))
      return content; 
    Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
    cipher.init(Cipher.ENCRYPT_MODE, aesKey);
    byte[] encrypted = cipher.doFinal(content.getBytes("utf-8"));
    return Base64.getEncoder().encodeToString(encrypted);
  }
  
  public static String decrypt(String content, SecretKeySpec aesKey) throws Exception {
    if (StringUtils.isEmpty(content))
      return content;
    Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
    cipher.init(Cipher.DECRYPT_MODE, aesKey);
    byte[] base64Data = Base64.getDecoder().decode(content);
    byte[] original = cipher.doFinal(base64Data);
    String originalString = new String(original, "utf-8");
    return originalString;
  }
  
  public static SecretKeySpec getSecretKey(String password) throws Exception {
    if (password == null || password == "" || password.length() != 16)
      return null; 
    byte[] keyBytes = Arrays.copyOf(password.getBytes("ASCII"), 16);
    return new SecretKeySpec(keyBytes, "AES");
  }
  
  public static SecretKeySpec getSecretKey32(String password) throws Exception {
    if (password == null || password == "" || password.length() != 32)
      return null; 
    byte[] keyBytes = Arrays.copyOf(password.getBytes("ASCII"), 32);
    return new SecretKeySpec(keyBytes, "AES");
  }
}
