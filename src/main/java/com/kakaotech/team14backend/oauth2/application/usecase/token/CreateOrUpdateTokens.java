package com.kakaotech.team14backend.oauth2.application.usecase.token;

import com.kakaotech.team14backend.oauth2.dto.SetTokenDTO;
import com.kakaotech.team14backend.member.domain.Member;
import com.kakaotech.team14backend.oauth2.application.command.token.CreateAccessToken;
import com.kakaotech.team14backend.oauth2.application.command.token.CreateRefreshToken;
import com.kakaotech.team14backend.oauth2.infrastructure.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateOrUpdateTokens {
  private final RefreshTokenRepository refreshTokenRepository;
  private final CreateAccessToken createAccessToken;
  private final CreateRefreshToken  createRefreshToken;
  public SetTokenDTO execute(Member member) {
    refreshTokenRepository.deleteRefreshToken(member.getKakaoId());
    String accessToken = createAccessToken.execute(member);
    String refreshToken = createRefreshToken.execute(member);
    return new SetTokenDTO(accessToken, refreshToken);
  }
}
