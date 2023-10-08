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
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {
  public static final Long accessEXP = 1000L * 60 * 60 * 48;
  public static final Long refreshEXP = 1000L * 60 * 60 * 168;
  public static final String TOKEN_PREFIX = "Bearer ";
  public static final String HEADER = "Authorization";
  public static final String SECRET = "MySecretKey";

  private final RefreshTokenRepository refreshTokenRepository;
  private final MemberRepository memberRepository;


  public String createToken(Member member) {
    String jwt = JWT.create()
        .withExpiresAt(new Date(System.currentTimeMillis() + accessEXP))
        .withClaim("kakaoId", member.getKakaoId())
        .withClaim("username", member.getUserName())
        .withClaim("instaId", member.getInstaId())
        .withClaim("role", member.getRole().toString())
        .sign(Algorithm.HMAC512(SECRET));
    return TOKEN_PREFIX + jwt;
  }

  public String createRefreshToken(Member member) {
    String jwt = JWT.create()
        .withExpiresAt(new Date(System.currentTimeMillis() + refreshEXP))
        .withClaim("kakaoId", member.getKakaoId())
        .withClaim("username", member.getUserName())
        .withClaim("instaId", member.getInstaId())
        .sign(Algorithm.HMAC512(SECRET));
    RefreshToken refreshToken = new RefreshToken(jwt, member.getKakaoId());
    refreshTokenRepository.save(refreshToken);
    return TOKEN_PREFIX + jwt;

  }

  public static DecodedJWT verifyToken(String jwt) throws SignatureVerificationException, TokenExpiredException {

    DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SECRET))
        .build().verify(jwt.replace(TokenService.TOKEN_PREFIX, ""));
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

  public ReissueDTO reissueAccessToken(String bearerToken){
    String refreshToken = bearerToken.replace("Bearer ", "");
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


  public TokenDTO createOrUpdateToken(HttpServletResponse response, Member member){
    String kakaoId = member.getKakaoId();
    String rtkInRedis = Optional.ofNullable(refreshTokenRepository.deleteRefreshToken(kakaoId))
        .orElseThrow(() -> new TokenValidationException(MessageCode.INCORRECT_REFRESH_TOEKN));
    String accessToken = this.createToken(member);
    String refreshToken = this.createRefreshToken(member);

    return new TokenDTO(accessToken, refreshToken);
  }
}


