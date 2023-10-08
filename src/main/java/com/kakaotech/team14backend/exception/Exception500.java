package com.kakaotech.team14backend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

// 서버 에러
@Getter
public class Exception500 extends RuntimeException {

  public Exception500(String message) {
    super(message);
  }

  public HttpStatus status() {
    return HttpStatus.INTERNAL_SERVER_ERROR;
  }
}
