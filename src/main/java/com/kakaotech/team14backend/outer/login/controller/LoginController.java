package com.kakaotech.team14backend.outer.login.controller;

import com.kakaotech.team14backend.auth.PrincipalDetails;
import com.kakaotech.team14backend.common.ApiResponse;
import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.jwt.service.TokenService;
import com.kakaotech.team14backend.outer.login.dto.GetInstagramCode;
import com.kakaotech.team14backend.outer.login.dto.GetKakaoCode;
import com.kakaotech.team14backend.outer.login.dto.KakaoProfileDTO;
import com.kakaotech.team14backend.outer.login.service.InstagramService;
import com.kakaotech.team14backend.outer.login.service.LoginService;
import com.kakaotech.team14backend.outer.login.service.LogoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

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
    System.out.println(kakaoCode.getCode());
    String kakaoAccessToken = loginService.getKaKaoAccessToken(kakaoCode.getCode());
    System.out.println(kakaoAccessToken);
    KakaoProfileDTO kakaoProfileDTO = loginService.getKakaoUserInfo(kakaoAccessToken);
    loginService.createOrLoginMember(kakaoProfileDTO);
    ApiResponse<?> apiResponse = loginService.AuthenticationSuccessHandelr(response,kakaoProfileDTO);
    return apiResponse;
  }


  @PostMapping("/api/user/instagram")
  @ResponseBody
  public ApiResponse<?> instagramConnect(HttpServletResponse response,@RequestBody GetInstagramCode instagramCode, @AuthenticationPrincipal PrincipalDetails principalDetails) {
    String kakaoId = principalDetails.getKakaoId();
    String InstagramAccessToken = instagramService.getAccessToken(instagramCode.getCode());
    instagramService.getInstagramAndSetNewToken(kakaoId,InstagramAccessToken);

    ApiResponse<?> apiResponse = instagramService.connectInstagramSuccessHandler(response,kakaoId);

    return apiResponse;
  }

  @GetMapping("/api/user/logout")
  @ResponseBody
  public ApiResponse<?> logout(HttpServletRequest request,@AuthenticationPrincipal PrincipalDetails principalDetails){
    String kakaoId = principalDetails.getKakaoId();
    ApiResponse<?> apiResponse = logoutService.logout(request,kakaoId);
    return apiResponse;
  }



}
