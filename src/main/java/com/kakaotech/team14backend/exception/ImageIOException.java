package com.kakaotech.team14backend.exception;

import com.kakaotech.team14backend.common.MessageCode;
import lombok.Getter;

@Getter
public class ImageIOException extends RuntimeException{

  public final MessageCode messageCode;
  public ImageIOException() {
    this.messageCode = MessageCode.IMAGE_NOT_SAVE;
  }
}
