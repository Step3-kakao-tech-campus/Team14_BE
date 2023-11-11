package com.kakaotech.team14backend.oauth2.application.usecase.login;

import com.kakaotech.team14backend.oauth2.application.command.login.AuthenticationSuccessHandler;
import com.kakaotech.team14backend.oauth2.application.command.login.CreateKakaoUser;
import com.kakaotech.team14backend.oauth2.application.command.login.GetKakaoAccessToken;
import com.kakaotech.team14backend.oauth2.application.command.login.GetKakaoUserInfo;
import com.kakaotech.team14backend.oauth2.dto.GetKakaoCodeDTO;
import com.kakaotech.team14backend.oauth2.dto.SetKakaoProfileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CreateOrLogin {
  private final GetKakaoAccessToken getKakaoAccessToken;
  private final GetKakaoUserInfo getKakaoUserInfo;
  private final CreateKakaoUser createkakaoUser;
  private final AuthenticationSuccessHandler authenticationSuccessHandler;

  @Transactional
  public void execute(GetKakaoCodeDTO getKakaoCodeDTO, HttpServletResponse response) throws IOException {
    String kakaoAccessToken = getKakaoAccessToken.execute(getKakaoCodeDTO.getCode());
    SetKakaoProfileDTO setKakaoProfileDTO = getKakaoUserInfo.execute(kakaoAccessToken);
    createkakaoUser.execute(setKakaoProfileDTO);
    authenticationSuccessHandler.handleKakaoSuccess(response, setKakaoProfileDTO);
  }

}

