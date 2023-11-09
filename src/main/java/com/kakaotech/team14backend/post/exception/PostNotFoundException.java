package com.kakaotech.team14backend.post.exception;

import com.kakaotech.team14backend.common.MessageCode;
import lombok.Getter;

@Getter
public class PostNotFoundException extends RuntimeException{

  public final MessageCode messageCode;
  public PostNotFoundException() {
    this.messageCode = MessageCode.POST_NOT_FOUND;
  }

}
