package com.kakaotech.team14backend.oauth2.application;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class GetInstagramAccessToken {

  @Value("${oauth2.instagram.client-id}")
  private String CLIENT_ID;
  @Value("${oauth2.instagram.client-secret}")
  private String CLIENT_SECRET;
  @Value("${oauth2.instagram.redirect-uri}")
  private String REDIRECT_URI;
  @Value("${oauth2.instagram.token-url}")
  private String TOKEN_URL;

  private final RestTemplate proxyRestTemplate;

  @Autowired
  public GetInstagramAccessToken(
      @Qualifier("proxyRestTemplate") RestTemplate proxyRestTemplate) {
    this.proxyRestTemplate = proxyRestTemplate;
  }
  public String getAccessToken(String code) {
    String GRANT_TYPE = "authorization_code";
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
// 요청 파라미터 설정
    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.add("client_id", CLIENT_ID);
    map.add("client_secret", CLIENT_SECRET);
    map.add("grant_type", GRANT_TYPE);
    map.add("redirect_uri", REDIRECT_URI);
    map.add("code", code);

    // Instagram API 호출
    String accessTokenRequestUrl = TOKEN_URL;
    HttpEntity<MultiValueMap<String, String>> InstaTokenRequest = new HttpEntity<>(map, headers);
    ResponseEntity<Map> response = proxyRestTemplate.postForEntity(
        accessTokenRequestUrl,
        InstaTokenRequest,
        Map.class);
    // 응답 받은 JSON 데이터 반환
    return (String) response.getBody().get("access_token");
  }
}
