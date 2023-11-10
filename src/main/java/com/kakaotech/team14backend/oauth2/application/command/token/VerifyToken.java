package com.kakaotech.team14backend.oauth2.application.command.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class VerifyToken {
  private static String SECRET;

  @Value("${jwt.secret}")
  public void setSecret(String secret) {
    SECRET = secret;
  }
  public static String TOKEN_PREFIX = "Bearer ";
  public static String HEADER = "Authorization";

  public static DecodedJWT execute(String jwt) throws SignatureVerificationException, TokenExpiredException {
    DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SECRET))
        .build().verify(jwt.replace(TOKEN_PREFIX, ""));
    return decodedJWT;
  }
}
