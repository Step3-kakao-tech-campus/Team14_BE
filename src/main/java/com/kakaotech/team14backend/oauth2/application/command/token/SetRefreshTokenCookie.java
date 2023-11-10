package com.kakaotech.team14backend.oauth2.application.command.token;

import com.kakaotech.team14backend.oauth2.dto.SetTokenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class SetRefreshTokenCookie {
  @Value("${jwt.token-validity-in-seconds-refreshToken}")
  private int cookieAge;
  public void execute(SetTokenDTO setTokenDTO, HttpServletResponse response){
    response.setContentType("application/json");
    // 토큰을 HTTP 헤더에 추가
    response.addHeader("Authorization", setTokenDTO.getAccessToken());
    // 리프레시토큰을 쿠키에 저장한다.
    String refreshTokenCookieName = "RefreshToken";
    String refreshTokenCookieValue = setTokenDTO.getRefreshToken();
    Cookie refreshTokenCookie = new Cookie(refreshTokenCookieName, refreshTokenCookieValue);
    refreshTokenCookie.setHttpOnly(true);  //httponly 옵션 설정
    refreshTokenCookie.setSecure(true); //https 옵션 설정
    refreshTokenCookie.setPath("/"); // 모든 곳에서 쿠키열람이 가능하도록 설정
    refreshTokenCookie.setMaxAge(cookieAge); //쿠키 만료시간 설정
    response.addCookie(refreshTokenCookie);
  }
}
