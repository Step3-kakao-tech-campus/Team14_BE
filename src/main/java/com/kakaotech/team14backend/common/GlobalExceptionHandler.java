package com.kakaotech.team14backend.common;

import com.kakaotech.team14backend.image.exception.ImageIOException;
import com.kakaotech.team14backend.member.exception.MemberNotFoundException;
import com.kakaotech.team14backend.point.exception.NotEnoughPointException;
import com.kakaotech.team14backend.post.exception.ExtentionNotAllowedException;
import com.kakaotech.team14backend.post.exception.LastPostIdParameterException;
import com.kakaotech.team14backend.post.exception.SizeParameterException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ImageIOException.class)
  public ApiResponse<ApiResponse.CustomBody> handleException(ImageIOException e) {
    return ApiResponseGenerator.fail(e.getMessageCode().getCode(), e.getMessageCode().getValue(),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(NotEnoughPointException.class)
  public ApiResponse<ApiResponse.CustomBody> handleException(NotEnoughPointException e) {
    return ApiResponseGenerator.fail(e.getMessageCode().getCode(), e.getMessageCode().getValue(),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(SizeParameterException.class)
  public ApiResponse<ApiResponse.CustomBody> handleSizeParameterException(
      SizeParameterException e) {
    return ApiResponseGenerator.fail(e.getMessageCode().getCode(), e.getMessageCode().getValue(),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(LastPostIdParameterException.class)
  public ApiResponse<ApiResponse.CustomBody> handleLastPostIdParameterException(
      LastPostIdParameterException e) {
    return ApiResponseGenerator.fail(e.getMessageCode().getCode(), e.getMessageCode().getValue(),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MemberNotFoundException.class)
  public ApiResponse<ApiResponse.CustomBody> handleMemberNotFoundException(
      MemberNotFoundException memberNotFoundException) {
    return ApiResponseGenerator.fail(memberNotFoundException.getMessageCode().getCode(),
        memberNotFoundException.getMessageCode().getValue(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ExtentionNotAllowedException.class)
  public ApiResponse<ApiResponse.CustomBody> handleMemberNotFoundException(
      ExtentionNotAllowedException extentionNotAllowedException) {
    return ApiResponseGenerator.fail(extentionNotAllowedException.getMessageCode().getCode(),
        extentionNotAllowedException.getMessageCode().getValue(), HttpStatus.BAD_REQUEST);
  }

}
