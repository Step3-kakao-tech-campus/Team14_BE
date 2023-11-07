package com.kakaotech.team14backend.exception;

import com.kakaotech.team14backend.common.MessageCode;
import lombok.Getter;

@Getter
public class NotEnoughPointException  extends RuntimeException{

  public final MessageCode messageCode;

  public NotEnoughPointException(MessageCode messageCode) {
    this.messageCode = messageCode;
  }
}
