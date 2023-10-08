package com.kakaotech.team14backend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

// 인증 안됨
@Getter
public class Exception401 extends RuntimeException {

  public Exception401(String message) {
    super(message);
  }

  public HttpStatus status() {
    return HttpStatus.UNAUTHORIZED;
  }
}
