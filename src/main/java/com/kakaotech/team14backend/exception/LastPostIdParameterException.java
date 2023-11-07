package com.kakaotech.team14backend.exception;

import com.kakaotech.team14backend.common.MessageCode;
import lombok.Getter;

@Getter
public class LastPostIdParameterException extends RuntimeException {

  private final MessageCode messageCode;

  public LastPostIdParameterException(MessageCode messageCode) {
    this.messageCode = messageCode;
  }
}
