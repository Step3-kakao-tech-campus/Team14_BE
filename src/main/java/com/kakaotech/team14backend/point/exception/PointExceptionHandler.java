package com.kakaotech.team14backend.point.exception;

import com.kakaotech.team14backend.common.ApiResponse;
import com.kakaotech.team14backend.common.ApiResponseGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.kakaotech.team14backend.point")
public class PointExceptionHandler {
  @ExceptionHandler(NotEnoughPointException.class)
  public ApiResponse<ApiResponse.CustomBody> handleException(NotEnoughPointException e) {
    return ApiResponseGenerator.fail(e.getMessageCode().getCode(), e.getMessageCode().getValue(),
        HttpStatus.BAD_REQUEST);
  }
}
