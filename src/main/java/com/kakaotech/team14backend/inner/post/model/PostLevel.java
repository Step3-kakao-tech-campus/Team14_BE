package com.kakaotech.team14backend.inner.post.model;

public enum PostLevel {

  LV3(1, 10),
  LV2(11, 20),
  LV1(21, 30),
  ;
  private final int start;
  private final int end;

  PostLevel(final int start, final int end) {
    this.start = start;
    this.end = end;
  }

  public static PostLevel from(final int level) {
    return switch (level) {
      case 1 -> LV1;
      case 2 -> LV2;
      case 3 -> LV3;
      default -> throw new IllegalArgumentException("Invalid level: %d".formatted(level));
    };
  }

  public static Integer to(final int rank) {
    if(rank <= 10) {
      return 1;
    } else if(rank <= 20) {
      return 2;
    } else{
      return 3;
    }
  }

  public int start() {
    return start;
  }

  public int end() {
    return end;
  }

}
