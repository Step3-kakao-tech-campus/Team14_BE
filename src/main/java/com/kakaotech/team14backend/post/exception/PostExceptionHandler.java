package com.kakaotech.team14backend.post.exception;

import com.kakaotech.team14backend.common.ApiResponse;
import com.kakaotech.team14backend.common.ApiResponseGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.kakaotech.team14backend.post")
public class PostExceptionHandler {

  @ExceptionHandler(PostLevelOutOfRangeException.class)
  public ApiResponse<ApiResponse.CustomBody> handleException(PostLevelOutOfRangeException e) {
    return ApiResponseGenerator.fail(e.getMessageCode().getCode(), e.getMessageCode().getValue(),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(PostNotFoundException.class)
  public ApiResponse<ApiResponse.CustomBody> handleMemberNotFoundException(
      PostNotFoundException postNotFoundException) {
    return ApiResponseGenerator.fail(postNotFoundException.getMessageCode().getCode(),
        postNotFoundException.getMessageCode().getValue(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(MaxLevelSizeException.class)
  public ApiResponse<ApiResponse.CustomBody> handleMemberNotFoundException(
      MaxLevelSizeException maxLevelSizeException) {
    return ApiResponseGenerator.fail(maxLevelSizeException.getMessageCode().getCode(),
        maxLevelSizeException.getMessageCode().getValue(), HttpStatus.BAD_REQUEST);
  }
}
