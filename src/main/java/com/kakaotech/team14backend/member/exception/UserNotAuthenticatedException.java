package com.kakaotech.team14backend.member.exception;

import com.kakaotech.team14backend.common.MessageCode;
import lombok.Getter;

@Getter
public class UserNotAuthenticatedException extends RuntimeException {

  private final MessageCode messageCode;

  public UserNotAuthenticatedException(MessageCode messageCode) {
    this.messageCode = messageCode;
  }
}
