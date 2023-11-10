package com.kakaotech.team14backend.oauth2.application.command.login;

import com.kakaotech.team14backend.oauth2.application.command.token.SetRefreshTokenCookie;
import com.kakaotech.team14backend.oauth2.dto.TokenDTO;
import com.kakaotech.team14backend.member.domain.Member;
import com.kakaotech.team14backend.member.infrastructure.MemberRepository;
import com.kakaotech.team14backend.oauth2.application.usecase.token.CreateOrUpdateTokens;
import com.kakaotech.team14backend.oauth2.dto.KakaoProfileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class AuthenticationSuccessHandler {


  private final MemberRepository memberRepository;
  private final CreateOrUpdateTokens createOrUpdateTokens;
  private final SetRefreshTokenCookie setRefreshTokenCookie;

  public void handleKakaoSuccess(HttpServletResponse response, KakaoProfileDTO
      kakaoProfileDTO) {
    String kakaoId = kakaoProfileDTO.getId();
    Member member = memberRepository.findByKakaoId(kakaoId);
    TokenDTO tokenDTO = createOrUpdateTokens.execute(member);
    setRefreshTokenCookie.execute(tokenDTO,response);
  }
}
