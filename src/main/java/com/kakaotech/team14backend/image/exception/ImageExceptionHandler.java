package com.kakaotech.team14backend.image.exception;

import com.kakaotech.team14backend.common.ApiResponse;
import com.kakaotech.team14backend.common.ApiResponseGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.kakaotech.team14backend.image")
public class ImageExceptionHandler {

  @ExceptionHandler(ImageIOException.class)
  public ApiResponse<ApiResponse.CustomBody> handleException(ImageIOException e) {
    return ApiResponseGenerator.fail(e.getMessageCode().getCode(), e.getMessageCode().getValue(),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
