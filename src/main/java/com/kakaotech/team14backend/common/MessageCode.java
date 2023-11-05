package com.kakaotech.team14backend.common;


public enum MessageCode {

  NOT_ALLOWED_FILE_EXT("4003","파일 확장명은 pdf, jpg, jpeg, png만 가능합니다."),
  INVALIDATE_REFRESH_TOKEN("401","잘못된 리프레시 토큰입니다."),
  INVALIDATE_ACCESS_TOKEN("401","잘못된 엑세스 토큰입니다.")
  ,
  INCORRECT_REFRESH_TOEKN("401","요청된 리프레시토큰과 불일치합니다.")
  ,
  EXPIRED_ACCESS_TOKEN("401","만료된 토큰입니다.")
  ,
  NOT_REGISTER_MEMBER("401","회원정보가 존재하지 않습니다")
  ,
  LEVEL_SIZE_SMALLER_THAN_20("403","각 레벨당 사이즈는 10보다 작아야합니다."),

  NOT_REGISTER_POST("444","해당 게시물이 존재하지 않습니다."),

  POST_MUST_FOUND_ONE("888","게시물이 1개만 조회되어야 합니다.")

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

