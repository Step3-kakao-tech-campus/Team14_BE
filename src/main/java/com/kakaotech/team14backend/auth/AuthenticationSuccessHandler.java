package com.kakaotech.team14backend.auth;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaotech.team14backend.common.ApiResponse;
import com.kakaotech.team14backend.common.ApiResponseGenerator;
import com.kakaotech.team14backend.exception.TokenValidationException;
import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.member.repository.MemberRepository;
import com.kakaotech.team14backend.jwt.service.TokenService;
import com.kakaotech.team14backend.jwt.dto.TokenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class AuthenticationSuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {

  private final TokenService tokenService;
  private final MemberRepository memberRepository;
  @Value("${jwt.token-validity-in-seconds-refreshToken}")
  private int cookieAge;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

    PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
    String kakaoId = Long.toString(principalDetails.getAttribute("id"));
    Member member = memberRepository.findByKakaoId(kakaoId);
    TokenDTO tokenDTO = tokenService.createOrUpdateToken(response, member);
//    ApiResponse<?> apiResponse = new ApiResponse<>(new ApiResponse.CustomBody(true,"accessToken:" + jwt,null), HttpStatus.OK);


    response.setContentType("application/json");
    // 토큰을 HTTP 헤더에 추가
    response.addHeader("Authorization", tokenDTO.getAccessToken());



    // 리프레시토큰을 쿠키에 저장한다.
    String refreshTokenCookieName = "RefreshToken";
    String refreshTokenCookieValue = tokenDTO.getRefreshToken();
    Cookie refreshTokenCookie = new Cookie("RefreshToken",refreshTokenCookieValue);
    refreshTokenCookie.setHttpOnly(true);  //httponly 옵션 설정
    refreshTokenCookie.setSecure(true); //https 옵션 설정
    refreshTokenCookie.setPath("/"); // 모든 곳에서 쿠키열람이 가능하도록 설정
    refreshTokenCookie.setMaxAge(cookieAge); //쿠키 만료시간 설정
    response.addCookie(refreshTokenCookie);

    //화면에 응답결과 출력
    ApiResponse<?> apiResponse = ApiResponseGenerator.success(HttpStatus.OK);
    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
    response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse.getBody()));
  }
}



