package com.kakaotech.team14backend.inner.point.model;

public enum PointPolicy {

  GET_500_WHEN_LIKE_UP("게시물 좋아요 수 50개 이상",500L),
  GET_200_WHEN_LIKE_UP("게시물 좋아요 수 20개 이상",200L),
  GET_100_WHEN_LINK_SNS("SNS 계정 연결",100L),
  GET_100_WHEN_SIGN_UP("최초 회원가입",500L),
  GET_100_REPEAT("자동 추가",100L),
  GET_20_WHEN_LIKE_UP("좋아요 누를 시",20L),

  GIVE_300_WHEN_UPLOAD("사진 업로드",300L),

  USE_100_FOR_MAIN_FEED_FIREWORK("메인 피드 폭죽 사용", -100L),
  USE_200_FOR_POPULARITY_LV1_FIREWORK("인기도 Lv1 폭죽 사용 시", -200L),
  USE_300_FOR_POPULARITY_LV2_FIREWORK("인기도 Lv2 폭죽 사용 시", -300L),
  USE_400_FOR_POPULARITY_LV3_FIREWORK("인기도 Lv3 폭죽 사용 시", -400L);

  private final Long point;
  private final String name;

  PointPolicy(String name, Long point) {
    this.point = point;
    this.name = name;
  }
  public Long getPoint(){return this.point;}
  public String getName(){return this.name;}
}

//## 포인트 획득
//- **게시물 좋아요 수 50개 이상**: 500포인트
//- **게시물 좋아요 수 20개 이상**: 200포인트
//- **SNS 계정 연결**: 100포인트
//- **최초 회원가입**: 100포인트
//- **자동 추가**: 100포인트
//- **좋아요 누를 시**: 20포인트
//- **사진 업로드**: 300포인트
//
//## 포인트 사용
//
//- **메인 피드 폭죽 사용**: -100포인트
//- **인기도 Lv1 폭죽 사용 시**: -200포인트
//- **인기도 Lv2 폭죽 사용 시**: -300포인트
//- **인기도 Lv3 폭죽 사용 시**: -400포인트
