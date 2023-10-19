package com.kakaotech.team14backend.exception;

import com.kakaotech.team14backend.common.MessageCode;
import lombok.Getter;

@Getter
public class MultiplePostsFoundException extends RuntimeException{

  public final MessageCode messageCode;

  public MultiplePostsFoundException(MessageCode messageCode) {
    this.messageCode = messageCode;
  }

}
