package com.kakaotech.team14backend.oauth2.exception;

import com.kakaotech.team14backend.common.MessageCode;
import lombok.Getter;

@Getter
public class TokenValidationException extends RuntimeException{
  public final MessageCode messageCode;

  public TokenValidationException(MessageCode messageCode) {
    this.messageCode = messageCode;
  }


  public TokenValidationException(MessageCode messageCode, Exception e) {
    this.messageCode = messageCode;
  }
}
