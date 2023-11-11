package com.kakaotech.team14backend.oauth2.application.command.connect;

import com.kakaotech.team14backend.oauth2.application.command.token.SetRefreshTokenCookie;
import com.kakaotech.team14backend.oauth2.dto.SetTokenDTO;
import com.kakaotech.team14backend.oauth2.application.usecase.token.CreateOrUpdateTokens;
import com.kakaotech.team14backend.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class ConnectSuccessHandler {

  private final CreateOrUpdateTokens createOrUpdateToken;
  private final SetRefreshTokenCookie setRefreshTokenCookie;
  public void execute(HttpServletResponse response, Member member) {
    SetTokenDTO setTokenDTO = createOrUpdateToken.execute(member);
    setRefreshTokenCookie.execute(setTokenDTO,response);
  }
}
