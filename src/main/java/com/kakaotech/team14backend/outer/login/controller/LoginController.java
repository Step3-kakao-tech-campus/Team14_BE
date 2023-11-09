package com.kakaotech.team14backend.outer.login.controller;

import com.kakaotech.team14backend.auth.PrincipalDetails;
import com.kakaotech.team14backend.common.ApiResponse;
import com.kakaotech.team14backend.common.ApiResponseGenerator;
import com.kakaotech.team14backend.common.MessageCode;
import com.kakaotech.team14backend.exception.UserNotAuthenticatedException;
import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.oauth2.dto.GetInstagramCodeDTO;
import com.kakaotech.team14backend.oauth2.dto.GetKakaoCodeDTO;
import com.kakaotech.team14backend.oauth2.dto.KakaoProfileDTO;
import com.kakaotech.team14backend.outer.login.service.InstagramService;
import com.kakaotech.team14backend.outer.login.service.LoginService;
import com.kakaotech.team14backend.outer.login.service.LogoutService;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class LoginController {

  private final String GRANT_TYPE = "authorization_code";

  private final LogoutService logoutService;
  private final InstagramService instagramService;


  @GetMapping("/currentUser")
  @ResponseBody
  public String getCurrentUser(@AuthenticationPrincipal PrincipalDetails principalDetails) {
    validatePrincipalDetails(principalDetails);

    Member member = principalDetails.getMember();
    return "Currently authenticated user: " + member.toString();
  }



  @PostMapping("/api/user/instagram")
  @ResponseBody
  public ApiResponse<?> instagramConnect(HttpServletResponse response,
                                         @RequestBody GetInstagramCodeDTO instagramCode,
                                         @AuthenticationPrincipal PrincipalDetails principalDetails) {
    validatePrincipalDetails(principalDetails);

    String kakaoId = principalDetails.getKakaoId();
    String InstagramAccessToken = instagramService.getAccessToken(instagramCode.getCode());
    instagramService.getInstagramAndSetNewToken(kakaoId, InstagramAccessToken);
    ApiResponse<?> apiResponse = instagramService.connectInstagramSuccessHandler(response, kakaoId);
    return apiResponse;
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
