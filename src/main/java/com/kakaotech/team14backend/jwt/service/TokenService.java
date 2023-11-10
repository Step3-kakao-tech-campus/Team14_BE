package com.kakaotech.team14backend.jwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.kakaotech.team14backend.common.MessageCode;
import com.kakaotech.team14backend.exception.MemberNotFoundException;
import com.kakaotech.team14backend.exception.TokenValidationException;
import com.kakaotech.team14backend.member.domain.Member;
import com.kakaotech.team14backend.member.infrastructure.MemberRepository;
import com.kakaotech.team14backend.jwt.RefreshToken;
import com.kakaotech.team14backend.jwt.dto.ReissueDTO;
import com.kakaotech.team14backend.jwt.dto.TokenDTO;
import com.kakaotech.team14backend.jwt.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenService {
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

  public static String HEADER = "Authorization";

  private final RefreshTokenRepository refreshTokenRepository;
  private final MemberRepository memberRepository;

  public static DecodedJWT verifyToken(String jwt) {
    DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SECRET))
        .build().verify(jwt.replace(TOKEN_PREFIX, ""));
    return decodedJWT;
  }

  public String createToken(Member member) {
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

  public String createRefreshToken(Member member) {
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


  public ReissueDTO reissueAccessToken(String refreshToken) {
    if(refreshToken == null){
      throw new TokenValidationException(MessageCode.INVALIDATE_REFRESH_TOKEN);
    }

    try {
      DecodedJWT decodedJWT = verifyToken(refreshToken);
      String kakaoId = decodedJWT.getClaim("kakaoId").asString();
      String redisInRTK = refreshTokenRepository.findRTK(kakaoId)
          .orElseThrow(() -> new TokenValidationException(MessageCode.INVALIDATE_REFRESH_TOKEN));;
      if (!refreshToken.equals(redisInRTK)) {
        throw new TokenValidationException(MessageCode.INVALIDATE_REFRESH_TOKEN);
      }
      Member member = memberRepository.findByKakaoId(kakaoId);
      String newAccessToken = this.createToken(member);
      return new ReissueDTO(newAccessToken);

    } catch (TokenExpiredException | TokenValidationException ex) {
      throw new TokenValidationException(MessageCode.INVALIDATE_REFRESH_TOKEN);
    } catch (NullPointerException ne ) {
      throw new MemberNotFoundException(MessageCode.NOT_REGISTER_MEMBER);
    }
  }

  public TokenDTO createOrUpdateToken(Member member) {
    refreshTokenRepository.deleteRefreshToken(member.getKakaoId());
    String accessToken = this.createToken(member);
    String refreshToken = this.createRefreshToken(member);
    return new TokenDTO(accessToken, refreshToken);
  }
}


