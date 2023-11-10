package com.kakaotech.team14backend.point.domain;

import com.kakaotech.team14backend.post.exception.PostLevelOutOfRangeException;

public class UsePointDecider {
  public static final long LEVEL1_POINT = 200L;
  public static final long LEVEL2_POINT = 300L;
  public static final long LEVEL3_POINT = 400L;

  public static Long getPoint(Integer postLevel) {
    if (postLevel == 1) {
      return LEVEL1_POINT;
    } else if (postLevel == 2) {
      return LEVEL2_POINT;
    } else if (postLevel == 3) {
      return LEVEL3_POINT;
    }
    throw new PostLevelOutOfRangeException();
  }

}
