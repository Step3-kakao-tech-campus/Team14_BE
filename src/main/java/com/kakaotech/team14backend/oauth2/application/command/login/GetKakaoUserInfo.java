package com.kakaotech.team14backend.oauth2.application.command.login;

import com.kakaotech.team14backend.oauth2.dto.SetKakaoProfileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Component
public class GetKakaoUserInfo {
  @Value("${oauth2.kakao.user-info-uri}")
  private String KAKAO_USER_INFO_URI;
  private final RestTemplate proxyRestTemplate;
  @Autowired
  public GetKakaoUserInfo(
      @Qualifier("proxyRestTemplate") RestTemplate proxyRestTemplate) {
    this.proxyRestTemplate = proxyRestTemplate;
  }
  public SetKakaoProfileDTO execute(String AccessToken) throws IOException {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + AccessToken);
    headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
    HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);
    //Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음.
    ResponseEntity<SetKakaoProfileDTO> response = proxyRestTemplate.postForEntity(
        KAKAO_USER_INFO_URI,
        kakaoProfileRequest,
        SetKakaoProfileDTO.class
    );
    return response.getBody();
  }
}
