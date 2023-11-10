package com.kakaotech.team14backend.oauth2.application.command.login;

import com.kakaotech.team14backend.oauth2.application.command.token.SetRefreshTokenCookie;
import com.kakaotech.team14backend.oauth2.dto.SetTokenDTO;
import com.kakaotech.team14backend.member.domain.Member;
import com.kakaotech.team14backend.member.infrastructure.MemberRepository;
import com.kakaotech.team14backend.oauth2.application.usecase.token.CreateOrUpdateTokens;
import com.kakaotech.team14backend.oauth2.dto.SetKakaoProfileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class AuthenticationSuccessHandler {


  private final MemberRepository memberRepository;
  private final CreateOrUpdateTokens createOrUpdateTokens;
  private final SetRefreshTokenCookie setRefreshTokenCookie;

  public void handleKakaoSuccess(HttpServletResponse response, SetKakaoProfileDTO
      setKakaoProfileDTO) {
    String kakaoId = setKakaoProfileDTO.getId();
    Member member = memberRepository.findByKakaoId(kakaoId);
    SetTokenDTO setTokenDTO = createOrUpdateTokens.execute(member);
    setRefreshTokenCookie.execute(setTokenDTO,response);
  }
}
