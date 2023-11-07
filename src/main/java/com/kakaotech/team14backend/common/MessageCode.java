package com.kakaotech.team14backend.common;


public enum MessageCode {

  NOT_ALLOWED_FILE_EXT("4003", "파일 확장명은 pdf, jpg, jpeg, png만 가능합니다."),
  INVALID_SIZE_PARAMETER("400", "size 파라미터는 0보다 커야합니다."),
  INVALID_LAST_POST_ID_PARAMETER("400", "lastPostId 파라미터는 0보다 커야합니다."),
  USER_NOT_AUTHENTICATED("401", "유저가 인증되지 않았습니다."),
  NEED_LOGIN("401", "로그인이 필요합니다."),
  NEED_INSTAGRAM("401", "인스타 연동이 필요합니다."),
  INVALIDATE_REFRESH_TOKEN("401", "잘못된 리프레시 토큰입니다."),
  INVALIDATE_ACCESS_TOKEN("401", "잘못된 엑세스 토큰입니다."),
  INCORRECT_REFRESH_TOEKN("401", "요청된 리프레시토큰과 불일치합니다."),
  EXPIRED_ACCESS_TOKEN("4111", "만료된 토큰입니다."),
  NOT_REGISTER_MEMBER("401", "회원정보가 존재하지 않습니다"),
  LEVEL_SIZE_SMALLER_THAN_20("403", "각 레벨당 사이즈는 10보다 작아야합니다."),

  NOT_REGISTER_POST("444", "해당 게시물이 존재하지 않습니다."),
  NOT_REGISTER_POPULARPOST("445", "해당 인기 게시물이 존재하지 않습니다."),

  NOT_ENOUGH_POINT("446", "포인트가 부족합니다."),

  POST_MUST_FOUND_ONE("888","게시물이 1개만 조회되어야 합니다."),

  IMAGE_NOT_SAVE("999","이미지 저장에 실패하였습니다."),

  NOT_RANGE_POST_LEVEL("998","게시물 레벨이 범위를 벗어났습니다."),

  ;

  private final String code;
  private final String value;

  MessageCode(String code, String value) {
    this.code = code;
    this.value = value;
  }

  public String getCode() {
    return this.code;
  }

  public String getValue() {
    return this.value;
  }


}

