package com.kakaotech.team14backend.post.exception;

import com.kakaotech.team14backend.common.MessageCode;
import lombok.Getter;

@Getter
public class PostLevelOutOfRangeException extends RuntimeException{

  public final MessageCode messageCode;
  public PostLevelOutOfRangeException() {
    this.messageCode = MessageCode.NOT_RANGE_POST_LEVEL;
  }
}
