package com.xx.vote.type;

public interface SensitiveTypeHandler {
  SensitiveType getSensitiveType();
  
  String handle(Object paramObject);
}
