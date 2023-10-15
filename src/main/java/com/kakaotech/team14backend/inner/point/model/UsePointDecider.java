package com.kakaotech.team14backend.inner.point.model;

public class UsePointDecider {
  public static Long decidePoint(Integer postLevel) {
    if (postLevel == 1) {
      return 200L;
    } else if (postLevel == 2) {
      return 300L;
    } else if (postLevel == 3) {
      return 400L;
    }
    throw new IllegalArgumentException("postLevel이 1,2,3이 아닙니다.");
  }

}
