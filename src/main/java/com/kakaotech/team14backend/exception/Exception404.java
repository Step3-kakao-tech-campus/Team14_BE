package com.kakaotech.team14backend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class Exception404 extends RuntimeException {

  public Exception404(String message) {
    super(message);
  }

  public HttpStatus status() {
    return HttpStatus.NOT_FOUND;
  }
}
