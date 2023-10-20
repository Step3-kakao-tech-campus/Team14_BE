package com.kakaotech.team14backend.jwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import com.kakaotech.team14backend.common.MessageCode;
import com.kakaotech.team14backend.exception.MemberNotFoundException;
import com.kakaotech.team14backend.exception.TokenValidationException;
import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.member.repository.MemberRepository;
import com.kakaotech.team14backend.jwt.RefreshToken;
import com.kakaotech.team14backend.jwt.dto.ReissueDTO;
import com.kakaotech.team14backend.jwt.dto.TokenDTO;
import com.kakaotech.team14backend.jwt.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Optional;

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


  public String createToken(Member member) {
    String jwt = JWT.create()
        .withExpiresAt(new Date(System.currentTimeMillis() + accessEXP * 1000))
        .withClaim("memberId",member.getMemberId().toString())
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

  public static DecodedJWT verifyToken(String jwt) throws SignatureVerificationException, TokenExpiredException {
    DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SECRET))
        .build().verify(jwt.replace(TOKEN_PREFIX, ""));
    return decodedJWT;
  }

  public static boolean validateToken(String jwt) {
    try {
      DecodedJWT decodedJWT = JWT.decode(jwt.replace(TokenService.TOKEN_PREFIX, ""));
      Verification verification = JWT.require(Algorithm.HMAC512(SECRET));
      verification.build().verify(decodedJWT);

      return true;
    } catch (SignatureVerificationException | TokenExpiredException e) {
      return false;
    }
  }

  public ReissueDTO reissueAccessToken(String refreshToken){
    DecodedJWT decodedJWT;

    try {
      decodedJWT = JWT.require(Algorithm.HMAC512(SECRET)).build().verify(refreshToken);
    } catch (TokenValidationException e) {
      throw new TokenValidationException(MessageCode.INVALIDATE_REFRESH_TOKEN);
    }
    String kakaoId = decodedJWT.getClaim("kakaoId").asString();
    Optional<String> redisInRTKOptional = refreshTokenRepository.findRTK(kakaoId);
    String redisInRTK = redisInRTKOptional.orElseThrow(() -> new TokenValidationException(MessageCode.INVALIDATE_REFRESH_TOKEN));

    if (!refreshToken.equals(redisInRTK)) {
      throw new TokenValidationException(MessageCode.INVALIDATE_REFRESH_TOKEN);
    }
    try {
      Member member = memberRepository.findByKakaoId(kakaoId);

      String newAccessToken = this.createToken(member);
      return new ReissueDTO(newAccessToken);
    } catch (NullPointerException e) {
      throw new MemberNotFoundException(MessageCode.NOT_REGISTER_MEMBER);
    }
  }


  public TokenDTO createOrUpdateToken(Member member){
    String rtkInRedis = Optional.ofNullable(refreshTokenRepository.deleteRefreshToken(member.getKakaoId()))
        .orElse(null);
    String accessToken = this.createToken(member);
    String refreshToken = this.createRefreshToken(member);

    return new TokenDTO(accessToken, refreshToken);
  }
}


