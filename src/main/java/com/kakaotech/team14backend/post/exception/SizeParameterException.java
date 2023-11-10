package com.kakaotech.team14backend.post.exception;

import com.kakaotech.team14backend.common.MessageCode;
import lombok.Getter;

@Getter
public class SizeParameterException extends RuntimeException {

  private final MessageCode messageCode;

  public SizeParameterException(MessageCode messageCode) {
    this.messageCode = messageCode;
  }
}
