package com.kakaotech.team14backend.exception;

import com.kakaotech.team14backend.common.MessageCode;
import lombok.Getter;

@Getter
public class MemberNotFoundException extends RuntimeException{
  public final MessageCode messageCode;

  public MemberNotFoundException(MessageCode messageCode) {
    this.messageCode = messageCode;
  }

}
