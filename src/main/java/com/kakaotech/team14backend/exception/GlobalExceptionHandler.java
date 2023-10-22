package com.kakaotech.team14backend.exception;

import com.kakaotech.team14backend.common.ApiResponse;
import com.kakaotech.team14backend.common.ApiResponseGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MemberNotFoundException.class)
  public ApiResponse<ApiResponse.CustomBody> handleMemberNotFoundException(MemberNotFoundException memberNotFoundException) {
    return ApiResponseGenerator.fail(memberNotFoundException.getMessageCode().getCode(), memberNotFoundException.getMessageCode().getValue(), HttpStatus.BAD_REQUEST);
  }
  @ExceptionHandler(ExtentionNotAllowedException.class)
  public ApiResponse<ApiResponse.CustomBody> handleMemberNotFoundException(ExtentionNotAllowedException extentionNotAllowedException) {
    return ApiResponseGenerator.fail(extentionNotAllowedException.getMessageCode().getCode(), extentionNotAllowedException.getMessageCode().getValue(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(PostNotFoundException.class)
  public ApiResponse<ApiResponse.CustomBody> handleMemberNotFoundException(PostNotFoundException postNotFoundException) {
    return ApiResponseGenerator.fail(postNotFoundException.getMessageCode().getCode(), postNotFoundException.getMessageCode().getValue(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(MaxLevelSizeException.class)
  public ApiResponse<ApiResponse.CustomBody> handleMemberNotFoundException(MaxLevelSizeException maxLevelSizeException) {
    return ApiResponseGenerator.fail(maxLevelSizeException.getMessageCode().getCode(), maxLevelSizeException.getMessageCode().getValue(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MultiplePostsFoundException.class)
  public ApiResponse<ApiResponse.CustomBody> handleMemberNotFoundException(MultiplePostsFoundException multiplePostsFoundException) {
    return ApiResponseGenerator.fail(multiplePostsFoundException.getMessageCode().getCode(), multiplePostsFoundException.getMessageCode().getValue(), HttpStatus.INTERNAL_SERVER_ERROR);
  }





  private static final String EXCEPTION_400_CODE = "400";
  private static final String EXCEPTION_401_CODE = "401";
  private static final String EXCEPTION_403_CODE = "403";
  private static final String EXCEPTION_404_CODE = "404";
  private static final String EXCEPTION_500_CODE = "500";

  @ExceptionHandler(Exception400.class)
  public ApiResponse<ApiResponse.CustomBody> handleException400(Exception400 e) {
    return ApiResponseGenerator.fail(EXCEPTION_400_CODE, e.getMessage(), e.status());
  }

  @ExceptionHandler(Exception401.class)
  public ApiResponse<ApiResponse.CustomBody> handleException401(Exception401 e) {
    return ApiResponseGenerator.fail(EXCEPTION_401_CODE, e.getMessage(), e.status());
  }

  @ExceptionHandler(Exception403.class)
  public ApiResponse<ApiResponse.CustomBody> handleException403(Exception403 e) {
    return ApiResponseGenerator.fail(EXCEPTION_403_CODE, e.getMessage(), e.status());
  }

  @ExceptionHandler(Exception404.class)
  public ApiResponse<ApiResponse.CustomBody> handleException404(Exception404 e) {
    return ApiResponseGenerator.fail(EXCEPTION_404_CODE, e.getMessage(), e.status());
  }

  @ExceptionHandler(Exception500.class)
  public ApiResponse<ApiResponse.CustomBody> handleException500(Exception500 e) {
    return ApiResponseGenerator.fail(EXCEPTION_500_CODE, e.getMessage(), e.status());
  }
}
