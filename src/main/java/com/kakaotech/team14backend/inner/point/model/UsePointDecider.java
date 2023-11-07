package com.kakaotech.team14backend.inner.point.model;

import com.kakaotech.team14backend.common.MessageCode;
import com.kakaotech.team14backend.exception.PostLevelOutOfRangeException;

public class UsePointDecider {
  public static Long decidePoint(Integer postLevel) {
    if (postLevel == 1) {
      return 200L;
    } else if (postLevel == 2) {
      return 300L;
    } else if (postLevel == 3) {
      return 400L;
    }
    throw new PostLevelOutOfRangeException(MessageCode.NOT_RANGE_POST_LEVEL);
  }

}
