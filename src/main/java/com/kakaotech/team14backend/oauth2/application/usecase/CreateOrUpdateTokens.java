package com.kakaotech.team14backend.oauth2.application.usecase;

import com.kakaotech.team14backend.oauth2.dto.TokenDTO;
import com.kakaotech.team14backend.member.domain.Member;
import com.kakaotech.team14backend.oauth2.application.command.CreateAccessToken;
import com.kakaotech.team14backend.oauth2.application.command.CreateRefreshToken;
import com.kakaotech.team14backend.oauth2.infrastructure.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateOrUpdateTokens {
  private final RefreshTokenRepository refreshTokenRepository;
  private final CreateAccessToken createAccessToken;
  private final CreateRefreshToken  createRefreshToken;
  public TokenDTO execute(Member member) {
    refreshTokenRepository.deleteRefreshToken(member.getKakaoId());
    String accessToken = createAccessToken.execute(member);
    String refreshToken = createRefreshToken.execute(member);
    return new TokenDTO(accessToken, refreshToken);
  }
}
