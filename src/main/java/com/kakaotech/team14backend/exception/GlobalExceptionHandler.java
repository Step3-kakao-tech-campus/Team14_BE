package com.kakaotech.team14backend.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaotech.team14backend.common.ApiResponse;
import com.kakaotech.team14backend.common.ApiResponseGenerator;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(PostLevelOutOfRangeException.class)
  public ApiResponse<ApiResponse.CustomBody> handleException(PostLevelOutOfRangeException e) {
    return ApiResponseGenerator.fail(e.getMessageCode().getCode(), e.getMessageCode().getValue(),
        HttpStatus.BAD_REQUEST);
  }

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

  @ExceptionHandler(HttpClientErrorException.BadRequest.class)
  public ApiResponse<ApiResponse.CustomBody> handleHttpClientErrorExceptionBadRequest(
      HttpClientErrorException.BadRequest e) throws JsonProcessingException {
    String responseBody = e.getResponseBodyAsString();
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      Map<String, Object> responseMap = objectMapper.readValue(responseBody, Map.class);
      return ApiResponseGenerator.fail("400", responseMap.toString(), HttpStatus.BAD_REQUEST);
    } catch (Exception ex) {
      return ApiResponseGenerator.fail("400", responseBody, HttpStatus.BAD_REQUEST);
    }
  }


}
