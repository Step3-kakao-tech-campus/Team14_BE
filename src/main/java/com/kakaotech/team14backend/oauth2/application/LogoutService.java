package com.kakaotech.team14backend.oauth2.application;

import com.kakaotech.team14backend.member.infrastructure.MemberRepository;
import com.kakaotech.team14backend.jwt.repository.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
@AllArgsConstructor
public class LogoutService {

  private final MemberRepository memberRepository;
  private final RefreshTokenRepository refreshTokenRepository;

  public void logout(HttpServletResponse response, String kakaoId) {
    refreshTokenRepository.deleteRefreshToken(kakaoId);
    String refreshTokenCookieName = "RefreshToken";
    Cookie refreshTokenCookie = new Cookie(refreshTokenCookieName, null);
    refreshTokenCookie.setHttpOnly(true);
    refreshTokenCookie.setSecure(true);
    refreshTokenCookie.setPath("/");
    refreshTokenCookie.setMaxAge(0);
    response.addCookie(refreshTokenCookie);
  }
}
