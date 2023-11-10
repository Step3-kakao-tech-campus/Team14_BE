package com.kakaotech.team14backend.jwt.service;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.kakaotech.team14backend.common.MessageCode;
import com.kakaotech.team14backend.jwt.TokenValidationException;
import com.kakaotech.team14backend.jwt.dto.ReissueDTO;
import com.kakaotech.team14backend.jwt.dto.TokenDTO;
import com.kakaotech.team14backend.oauth2.infrastructure.RefreshTokenRepository;
import com.kakaotech.team14backend.member.domain.Member;
import com.kakaotech.team14backend.member.exception.MemberNotFoundException;
import com.kakaotech.team14backend.member.infrastructure.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
  @Value("${jwt.token-validity-in-seconds-accessToken}")
  private Long accessEXP;
  @Value("${jwt.token-validity-in-seconds-refreshToken}")
  private Long refreshEXP;
  @Value("${jwt.secret}")
  private static String SECRET;



  public static String TOKEN_PREFIX = "Bearer ";

  public static String HEADER = "Authorization";

  private final RefreshTokenRepository refreshTokenRepository;
  private final MemberRepository memberRepository;








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


