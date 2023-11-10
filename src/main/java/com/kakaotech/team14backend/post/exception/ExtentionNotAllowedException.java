package com.kakaotech.team14backend.post.exception;

import com.kakaotech.team14backend.common.MessageCode;
import lombok.Getter;

@Getter
public class ExtentionNotAllowedException extends RuntimeException {


  public final MessageCode messageCode;

  public ExtentionNotAllowedException(MessageCode messageCode){
    this.messageCode = messageCode;
  }

}

