package com.kakaotech.team14backend.common;

public enum RedisKey {

  POPULAR_POST_PREFIX("popularPost:"),
  VIEW_COUNT_PREFIX("postViewCount:")

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
