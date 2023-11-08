package com.kakaotech.team14backend.outer.login.service;

import com.kakaotech.team14backend.common.ApiResponse;
import com.kakaotech.team14backend.common.ApiResponseGenerator;
import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.member.model.Role;
import com.kakaotech.team14backend.inner.member.repository.MemberRepository;
import com.kakaotech.team14backend.jwt.dto.TokenDTO;
import com.kakaotech.team14backend.jwt.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Service
public class InstagramService {

  private final String GRANT_TYPE = "authorization_code";

  @Value("${oauth2.instagram.client-id}")
  private String CLIENT_ID;
  @Value("${oauth2.instagram.client-secret}")
  private String CLIENT_SECRET;

  @Value("${oauth2.instagram.redirect-uri}")
  private String REDIRECT_URI;
  @Value("${oauth2.instagram.token-url}")
  private String TOKEN_URL;
  @Value("${oauth2.instagram.user-info-url}")
  private String USER_INFO_URL;
  @Value("${jwt.token-validity-in-seconds-refreshToken}")
  private int cookieAge;

  private final RestTemplate proxyRestTemplate;
  private final MemberRepository memberRepository;
  private final TokenService tokenService;

  @Autowired
  public InstagramService(
      @Qualifier("proxyRestTemplate") RestTemplate proxyRestTemplate,
      MemberRepository memberRepository,
      TokenService tokenService) {
    this.proxyRestTemplate = proxyRestTemplate;
    this.memberRepository = memberRepository;
    this.tokenService = tokenService;
  }

  public String getAccessToken(String code) {
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

  @Transactional
  public void getInstagramAndSetNewToken(String kakaoId, String accessToken) {
    // 응답 받은 JSON 데이터 반환
    String userInfoUrl = USER_INFO_URL + "&access_token=" + accessToken;
    ResponseEntity<Map> userResponse = proxyRestTemplate.exchange(userInfoUrl,
        HttpMethod.GET,
        null,
        Map.class);
    String instaId = (String) userResponse.getBody().get("username");
    Member memberEntity = memberRepository.findByKakaoId(kakaoId);
    memberEntity.updateInstagram(Role.ROLE_USER, instaId);
  }

  public ApiResponse<?> connectInstagramSuccessHandler(HttpServletResponse response, String kakaoId) {

    Member member = memberRepository.findByKakaoId(kakaoId);
    TokenDTO tokenDTO = tokenService.createOrUpdateToken(member);

    response.setContentType("application/json");
    // 토큰을 HTTP 헤더에 추가
    response.addHeader("Authorization", tokenDTO.getAccessToken());
    // 리프레시토큰을 쿠키에 저장한다.
    String refreshTokenCookieName = "RefreshToken";
    String refreshTokenCookieValue = tokenDTO.getRefreshToken();
    Cookie refreshTokenCookie = new Cookie(refreshTokenCookieName, refreshTokenCookieValue);
    refreshTokenCookie.setHttpOnly(true);  //httponly 옵션 설정
    refreshTokenCookie.setSecure(true); //https 옵션 설정
    refreshTokenCookie.setPath("/"); // 모든 곳에서 쿠키열람이 가능하도록 설정
    refreshTokenCookie.setMaxAge(cookieAge); //쿠키 만료시간 설정
    response.addCookie(refreshTokenCookie);

    //화면에 응답결과 출력
    return ApiResponseGenerator.success(HttpStatus.OK);
  }


}





