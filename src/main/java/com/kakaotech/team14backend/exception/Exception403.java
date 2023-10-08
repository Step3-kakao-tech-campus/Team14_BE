package com.kakaotech.team14backend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

// 권한 없음
@Getter
public class Exception403 extends RuntimeException {

  public Exception403(String message) {
    super(message);
  }

  public HttpStatus status() {
    return HttpStatus.FORBIDDEN;
  }
}
