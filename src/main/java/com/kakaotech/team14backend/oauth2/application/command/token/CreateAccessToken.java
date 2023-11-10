package com.kakaotech.team14backend.oauth2.application.command.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.kakaotech.team14backend.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class CreateAccessToken {
  @Value("${jwt.token-validity-in-seconds-accessToken}")
  private Long accessEXP;
  @Value("${jwt.token-validity-in-seconds-refreshToken}")
  private Long refreshEXP;
  private static String SECRET;

  @Value("${jwt.secret}")
  public void setSecret(String secret) {
    SECRET = secret;
  }
  public static String TOKEN_PREFIX = "Bearer ";


  public String execute(Member member) {
    String jwt = JWT.create()
        .withExpiresAt(new Date(System.currentTimeMillis() + accessEXP * 1000))
        .withClaim("memberId", member.getMemberId().toString())
        .withClaim("kakaoId", member.getKakaoId())
        .withClaim("username", member.getUserName())
        .withClaim("instaId", member.getInstaId())
        .withClaim("role", member.getRole().toString())
        .sign(Algorithm.HMAC512(SECRET));
    return TOKEN_PREFIX + jwt;
  }
}
