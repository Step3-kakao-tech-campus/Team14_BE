package com.kakaotech.team14backend.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.kakaotech.team14backend.inner.member.model.Member;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {
  public static final Long EXP = 1000L * 60 * 60 * 48;
  public static final String TOKEN_PREFIX = "Bearer ";
  public static final String HEADER = "Authorization";
  public static final String SECRET = "MySecretKey";

  public static String create(Member member) {
    String jwt = JWT.create()
        .withExpiresAt(new Date(System.currentTimeMillis() + EXP))
        .withClaim("kakaoId", member.getKakaoId())
        .withClaim("username", member.getUserName())
        .withClaim("instaId", member.getInstaId())
        .withClaim("role", member.getRole())
        .sign(Algorithm.HMAC512(SECRET));
    return TOKEN_PREFIX + jwt;
  }

  public static DecodedJWT verify(String jwt) throws SignatureVerificationException, TokenExpiredException {
    jwt = jwt.replace(JwtProvider.TOKEN_PREFIX, "");
    DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SECRET))
        .build().verify(jwt);
    return decodedJWT;
  }
}
