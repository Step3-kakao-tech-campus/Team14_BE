package com.kakaotech.team14backend.inner.post.model;

public enum PostLevel {
  LV3_RANGE_START(1),
  LV3_RANGE_END(29),
  LV2_RANGE_START(30),
  LV2_RANGE_END(99),
  LV1_RANGE_START(100),
  LV1_RANGE_END(299);
  private int value;

  public int getValue() {
    return value;
  }

  PostLevel(int value) {
    this.value = value;
  }

  public static int[] getLevelRange(int postLevel) {
    if(postLevel == 3) {
      return new int[] {1, 29};
    } else if(postLevel == 2) {
      return new int[] {30, 99};
    } else if(postLevel == 1) {
      return new int[] {100, 299};
    } else {
      throw new IllegalArgumentException("Invalid level: " + postLevel);
    }
  }

}
