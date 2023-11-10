package com.kakaotech.team14backend.oauth2.presentation;

import com.kakaotech.team14backend.auth.PrincipalDetails;
import com.kakaotech.team14backend.common.ApiResponse;
import com.kakaotech.team14backend.common.ApiResponseGenerator;
import com.kakaotech.team14backend.common.MessageCode;
import com.kakaotech.team14backend.member.exception.UserNotAuthenticatedException;
import com.kakaotech.team14backend.oauth2.application.command.AuthenticationSuccessHandler;
import com.kakaotech.team14backend.oauth2.application.usecase.LoginService;
import com.kakaotech.team14backend.oauth2.application.usecase.LogoutService;
import com.kakaotech.team14backend.oauth2.dto.GetKakaoCodeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class LoginController {

  private final LoginService loginService;
  private final LogoutService logoutService;
  private final AuthenticationSuccessHandler authenticationSuccessHandler;

  @PostMapping("/api/login")
  @ResponseBody
  public ApiResponse<?> kakaoLogin(HttpServletResponse response,
                                   @RequestBody GetKakaoCodeDTO getKakaoCodeDTO) throws IOException {
    loginService.createOrLogin(getKakaoCodeDTO,response);
    return ApiResponseGenerator.success(HttpStatus.OK);
  }

  @PostMapping("/api/user/logout")
  @ResponseBody
  public ApiResponse<?> logout(HttpServletRequest request, HttpServletResponse response,
                               @AuthenticationPrincipal PrincipalDetails principalDetails) {
    validatePrincipalDetails(principalDetails);
    String kakaoId = principalDetails.getKakaoId();
    logoutService.logout(response, kakaoId);
    return ApiResponseGenerator.success(HttpStatus.OK);
  }

  private void validatePrincipalDetails(PrincipalDetails principalDetails) {
    if (principalDetails == null) {
      throw new UserNotAuthenticatedException(MessageCode.USER_NOT_AUTHENTICATED);
    }
  }
}
