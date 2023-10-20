package com.kakaotech.team14backend.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaotech.team14backend.common.ApiResponse;
import com.kakaotech.team14backend.common.ApiResponseGenerator;
import com.kakaotech.team14backend.exception.Exception401;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FilterResponseUtils {

  public static void unAuthorized(HttpServletResponse response) throws IOException {
    ApiResponse<?> apiResponse = ApiResponseGenerator.fail("401", "로그인이 필요합니다", HttpStatus.UNAUTHORIZED);
    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
    response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse.getBody()));
  }


  public static void forbidden(HttpServletResponse response, Boolean isRoleNotUser) throws IOException {
    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
    // 현재 사용자의 권한에 따라 다른 에러 메시지를 설정
    if (isRoleNotUser) {
      ApiResponse<?> apiResponse = ApiResponseGenerator.fail("403", "인스타연동이 필요합니다", HttpStatus.FORBIDDEN);
      response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse.getBody()));
//
    } else {
      unAuthorized(response);
    }
  }
}
