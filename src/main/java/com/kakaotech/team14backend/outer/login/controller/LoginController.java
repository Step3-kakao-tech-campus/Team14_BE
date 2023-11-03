package com.kakaotech.team14backend.outer.login.controller;

import com.kakaotech.team14backend.auth.PrincipalDetails;
import com.kakaotech.team14backend.common.ApiResponse;
import com.kakaotech.team14backend.common.ApiResponseGenerator;
import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.jwt.service.TokenService;
import com.kakaotech.team14backend.outer.login.dto.GetInstagramCode;
import com.kakaotech.team14backend.outer.login.dto.GetKakaoCode;
import com.kakaotech.team14backend.outer.login.dto.KakaoProfileDTO;
import com.kakaotech.team14backend.outer.login.service.InstagramService;
import com.kakaotech.team14backend.outer.login.service.LoginService;
import com.kakaotech.team14backend.outer.login.service.LogoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class LoginController {
  private final String GRANT_TYPE = "authorization_code";

  private final LoginService loginService;
  private final LogoutService logoutService;
  private final InstagramService instagramService;


  @GetMapping("/currentUser")
  @ResponseBody
  public String getCurrentUser(@AuthenticationPrincipal PrincipalDetails principalDetails) {
    Member member = principalDetails.getMember();
    return "Currently authenticated user: " + member.toString();
  }

  @PostMapping("/api/login")
  @ResponseBody
  public ApiResponse<?> kakaoLogin(HttpServletResponse response, @RequestBody GetKakaoCode kakaoCode) throws IOException {
    String kakaoAccessToken = loginService.getKaKaoAccessToken(kakaoCode.getCode());
    KakaoProfileDTO kakaoProfileDTO = loginService.getKakaoUserInfo(kakaoAccessToken);
    loginService.createOrLoginMember(kakaoProfileDTO);
    loginService.AuthenticationSuccessHandelr(response, kakaoProfileDTO);
    return ApiResponseGenerator.success(HttpStatus.OK);
  }


  @PostMapping("/api/user/instagram")
  @ResponseBody
  public ApiResponse<?> instagramConnect(HttpServletResponse response, @RequestBody GetInstagramCode instagramCode, @AuthenticationPrincipal PrincipalDetails principalDetails) {
    String kakaoId = principalDetails.getKakaoId();
    String InstagramAccessToken = instagramService.getAccessToken(instagramCode.getCode());
    instagramService.getInstagramAndSetNewToken(kakaoId, InstagramAccessToken);
    ApiResponse<?> apiResponse = instagramService.connectInstagramSuccessHandler(response, kakaoId);
    return apiResponse;
  }

  @PostMapping("/api/user/logout")
  @ResponseBody
  public ApiResponse<?> logout(HttpServletRequest request, HttpServletResponse response, @AuthenticationPrincipal PrincipalDetails principalDetails) {
    String kakaoId = principalDetails.getKakaoId();
    ApiResponse<?> apiResponse = logoutService.logout(request, response, kakaoId);
    return apiResponse;
  }


}
