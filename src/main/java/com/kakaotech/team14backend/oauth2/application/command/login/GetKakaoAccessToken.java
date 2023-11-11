package com.kakaotech.team14backend.oauth2.application.command.login;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class GetKakaoAccessToken {
  @Value("${oauth2.kakao.redirect-uri}")
  private String KAKAO_REDIRECT_URI;
  @Value("${oauth2.kakao.client-id}")
  private String KAKAO_CLIENT_ID;
  @Value("${oauth2.kakao.token-uri}")
  private String KAKAO_TOKEN_URI;

  private final RestTemplate proxyRestTemplate;
  @Autowired
  public GetKakaoAccessToken(
      @Qualifier("proxyRestTemplate") RestTemplate proxyRestTemplate) {
    this.proxyRestTemplate = proxyRestTemplate;
  }

  public String execute(String code){
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("grant_type", "authorization_code");
    params.add("client_id", KAKAO_CLIENT_ID);
    params.add("redirect_uri", KAKAO_REDIRECT_URI);
    params.add("code", code);

    HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

    ResponseEntity<Map> response = proxyRestTemplate.postForEntity(
        KAKAO_TOKEN_URI,
        kakaoTokenRequest,
        Map.class
    );

    return (String) response.getBody().get("access_token");
  }
}
