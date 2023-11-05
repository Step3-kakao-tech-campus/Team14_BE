package com.kakaotech.team14backend.inner.point.model;

public enum GetPointPolicy {

  GET_500_WHEN_LIKE_UP("게시물 좋아요 수 50개 이상", 500L),
  GET_200_WHEN_LIKE_UP("게시물 좋아요 수 20개 이상", 200L),
  GET_100_WHEN_LINK_SNS("SNS 계정 연결", 100L),
  GET_100_WHEN_SIGN_UP("최초 회원가입", 500L),
  GET_100_REPEAT("자동 추가", 100L),
  GET_20_WHEN_LIKE_UP("좋아요 누를 시", 20L),

  GIVE_300_WHEN_UPLOAD("사진 업로드", 300L),

  USE_100_WHEN_GET_INSTA_ID("인스타 아이디 조회", 100L);

  private final Long point;
  private final String name;

  GetPointPolicy(String name, Long point) {
    this.point = point;
    this.name = name;
  }

  public Long getPoint() {
    return this.point;
  }

  public String getName() {
    return this.name;
  }
}


