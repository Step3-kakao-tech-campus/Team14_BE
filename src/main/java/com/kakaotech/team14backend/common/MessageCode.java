package com.kakaotech.team14backend.common;


public enum MessageCode {

  NOT_ALLOWED_FILE_EXT("4003","파일 확장명은 pdf,img만 가능합니다.");
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

