package com.kakaotech.team14backend.point.exception;

import com.kakaotech.team14backend.common.MessageCode;
import lombok.Getter;

@Getter
public class NotEnoughPointException  extends RuntimeException{

  public final MessageCode messageCode;

  public NotEnoughPointException() {
    this.messageCode = MessageCode.NOT_ENOUGH_POINT;
  }
}
