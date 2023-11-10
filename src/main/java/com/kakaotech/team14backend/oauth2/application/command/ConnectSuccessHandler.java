package com.kakaotech.team14backend.oauth2.application.command;

import com.kakaotech.team14backend.oauth2.application.usecase.CreateOrUpdateTokens;
import com.kakaotech.team14backend.member.domain.Member;
import com.kakaotech.team14backend.oauth2.dto.TokenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class ConnectSuccessHandler {

  private final CreateOrUpdateTokens createOrUpdateToken;
  private final SetRefreshTokenCookie setRefreshTokenCookie;
  public void execute(HttpServletResponse response, Member member) {
    TokenDTO tokenDTO = createOrUpdateToken.execute(member);
    setRefreshTokenCookie.execute(tokenDTO,response);
  }
}
