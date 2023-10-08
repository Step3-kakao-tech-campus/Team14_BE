package com.kakaotech.team14backend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

// 유효성 검사 실패, 잘못된 파라미터 요청
@Getter
public class Exception400 extends RuntimeException {

  public Exception400(String message) {
    super(message);
  }

  public HttpStatus status() {
    return HttpStatus.BAD_REQUEST;
  }
}
