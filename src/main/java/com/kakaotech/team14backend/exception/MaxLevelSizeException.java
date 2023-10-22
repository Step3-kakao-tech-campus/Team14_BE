package com.kakaotech.team14backend.exception;

import com.kakaotech.team14backend.common.MessageCode;
import lombok.Getter;

@Getter
public class MaxLevelSizeException extends RuntimeException {

  public final MessageCode messageCode;

  public MaxLevelSizeException(MessageCode messageCode) {
    this.messageCode = messageCode;
  }

}
