package com.kakaotech.team14backend.oauth2.application.usecase;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.kakaotech.team14backend.common.MessageCode;
import com.kakaotech.team14backend.jwt.TokenValidationException;
import com.kakaotech.team14backend.jwt.dto.ReissueDTO;
import com.kakaotech.team14backend.member.domain.Member;
import com.kakaotech.team14backend.member.exception.MemberNotFoundException;
import com.kakaotech.team14backend.member.infrastructure.MemberRepository;
import com.kakaotech.team14backend.oauth2.application.command.CreateAccessToken;
import com.kakaotech.team14backend.oauth2.application.command.VerifyToken;
import com.kakaotech.team14backend.oauth2.infrastructure.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReissueAccessTokenService {
  private final CreateAccessToken createAccessToken;
  private final RefreshTokenRepository refreshTokenRepository;
  private final MemberRepository memberRepository;
  public ReissueDTO execute(String refreshToken) {
    if(refreshToken == null){
      throw new TokenValidationException(MessageCode.INVALIDATE_REFRESH_TOKEN);
    }

    try {
      DecodedJWT decodedJWT = VerifyToken.execute(refreshToken);
      String kakaoId = decodedJWT.getClaim("kakaoId").asString();
      String redisInRTK = refreshTokenRepository.findRTK(kakaoId)
          .orElseThrow(() -> new TokenValidationException(MessageCode.INVALIDATE_REFRESH_TOKEN));;
      if (!refreshToken.equals(redisInRTK)) {
        throw new TokenValidationException(MessageCode.INVALIDATE_REFRESH_TOKEN);
      }
      Member member = memberRepository.findByKakaoId(kakaoId);
      String newAccessToken = createAccessToken.execute(member);
      return new ReissueDTO(newAccessToken);

    } catch (TokenExpiredException | TokenValidationException ex) {
      throw new TokenValidationException(MessageCode.INVALIDATE_REFRESH_TOKEN);
    } catch (NullPointerException ne ) {
      throw new MemberNotFoundException(MessageCode.NOT_REGISTER_MEMBER);
    }
  }
}
