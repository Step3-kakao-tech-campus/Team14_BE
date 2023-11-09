package com.kakaotech.team14backend.exception;

import com.kakaotech.team14backend.common.MessageCode;
import lombok.Getter;

@Getter
public class MaxLevelSizeException extends RuntimeException {

  public final MessageCode messageCode;

  public MaxLevelSizeException() {
    this.messageCode = MessageCode.LEVEL_SIZE_SMALLER_THAN_20;
  }

}
