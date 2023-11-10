package com.kakaotech.team14backend.oauth2.application;

import com.kakaotech.team14backend.oauth2.dto.GetKakaoCodeDTO;
import com.kakaotech.team14backend.oauth2.dto.KakaoProfileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class LoginService {
  private final GetKakaoAccessToken getKakaoAccessToken;
  private final GetKakaoUserInfo getKakaoUserInfo;
  private final CreateKakaoUser createkakaoUser;
  private final AuthenticationSuccessHandler authenticationSuccessHandler;

  @Transactional
  public void createOrLogin(GetKakaoCodeDTO getKakaoCodeDTO, HttpServletResponse response) throws IOException {
    String kakaoAccessToken = getKakaoAccessToken.execute(getKakaoCodeDTO.getCode());
    KakaoProfileDTO kakaoProfileDTO = getKakaoUserInfo.execute(kakaoAccessToken);
    createkakaoUser.execute(kakaoProfileDTO);
    authenticationSuccessHandler.handleKakaoSuccess(response,kakaoProfileDTO);
  }

}

