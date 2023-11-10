package com.kakaotech.team14backend.oauth2.application.command.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.kakaotech.team14backend.oauth2.domain.RefreshToken;
import com.kakaotech.team14backend.member.domain.Member;
import com.kakaotech.team14backend.oauth2.infrastructure.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class CreateRefreshToken {
  @Value("${jwt.token-validity-in-seconds-accessToken}")
  private Long accessEXP;
  @Value("${jwt.token-validity-in-seconds-refreshToken}")
  private Long refreshEXP;
  private static String SECRET;

  @Value("${jwt.secret}")
  public void setSecret(String secret) {
    SECRET = secret;
  }
  private final RefreshTokenRepository refreshTokenRepository;
  public String execute(Member member) {
    String jwt = JWT.create()
        .withExpiresAt(new Date(System.currentTimeMillis() + refreshEXP * 1000))
        .withClaim("kakaoId", member.getKakaoId())
        .withClaim("username", member.getUserName())
        .withClaim("instaId", member.getInstaId())
        .sign(Algorithm.HMAC512(SECRET));
    RefreshToken refreshToken = new RefreshToken(jwt, member.getKakaoId());
    refreshTokenRepository.save(refreshToken);
    return jwt;

  }

}

