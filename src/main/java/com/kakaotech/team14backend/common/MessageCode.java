package com.kakaotech.team14backend.common;


public enum MessageCode {

  NOT_ALLOWED_FILE_EXT("4003","파일 확장명은 pdf,img만 가능합니다."),
  INVALIDATE_REFRESH_TOKEN("401","잘못된 리프레시 토큰입니다."),
  INVALIDATE_ACCESS_TOKEN("401","잘못된 엑세스 토큰입니다.")
  ,
  INCORRECT_REFRESH_TOEKN("401","요청된 리프레시토큰과 불일치합니다")
,
  NOT_REGISTER_MEMBER("401","회원정보가 존재하지 않습니다")
  ,;

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

