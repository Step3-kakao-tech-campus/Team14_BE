package com.kakaotech.team14backend.oauth2.presentation;

import com.kakaotech.team14backend.common.ApiResponse;
import com.kakaotech.team14backend.common.ApiResponseGenerator;
import com.kakaotech.team14backend.jwt.application.AuthenticationSuccessHandler;
import com.kakaotech.team14backend.oauth2.application.LoginService;
import com.kakaotech.team14backend.oauth2.dto.GetKakaoCodeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class LoginController {

  private final LoginService loginService;
  private final AuthenticationSuccessHandler authenticationSuccessHandler;

  @PostMapping("/api/login")
  @ResponseBody
  public ApiResponse<?> kakaoLogin(HttpServletResponse response,
                                   @RequestBody GetKakaoCodeDTO getKakaoCodeDTO) throws IOException {
    loginService.createOrLogin(getKakaoCodeDTO,response);
    return ApiResponseGenerator.success(HttpStatus.OK);
  }
}
