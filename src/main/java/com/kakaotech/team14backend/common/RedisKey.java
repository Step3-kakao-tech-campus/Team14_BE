package com.kakaotech.team14backend.common;

public enum RedisKey {

  POPULAR_POST("popularPost"),

  ;

  private final String key;

  RedisKey(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }

  @Override
  public String toString() {
    return key;
  }


}
