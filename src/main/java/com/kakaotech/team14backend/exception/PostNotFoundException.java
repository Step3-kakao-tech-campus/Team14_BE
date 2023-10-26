package com.kakaotech.team14backend.exception;

import com.kakaotech.team14backend.common.MessageCode;
import lombok.Getter;

@Getter
public class PostNotFoundException extends RuntimeException{
  
  public final MessageCode messageCode;
  public PostNotFoundException(MessageCode messageCode) {
    this.messageCode = messageCode;
  }

}
