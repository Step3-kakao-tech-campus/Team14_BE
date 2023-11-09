package com.kakaotech.team14backend.oauth2.application;

import com.kakaotech.team14backend.inner.member.repository.MemberRepository;
import com.kakaotech.team14backend.jwt.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class GetInstagramUserInfo {
  @Value("${oauth2.instagram.user-info-url}")
  private String USER_INFO_URL;
  private final RestTemplate proxyRestTemplate;

  @Autowired
  public GetInstagramUserInfo(
      @Qualifier("proxyRestTemplate") RestTemplate proxyRestTemplate) {
    this.proxyRestTemplate = proxyRestTemplate;
  }

  @Transactional
  public String execute(String accessToken) {
    // 응답 받은 JSON 데이터 반환
    String userInfoUrl = USER_INFO_URL + "&access_token=" + accessToken;
    RestTemplate rt = new RestTemplate();
    ResponseEntity<Map> userResponse = rt.exchange(userInfoUrl,
        HttpMethod.GET,
        null,
        Map.class);
    return (String) userResponse.getBody().get("username");
  }
}
