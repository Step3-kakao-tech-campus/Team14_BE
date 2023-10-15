package com.kakaotech.team14backend.outer.login.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.kakaotech.team14backend.common.ApiResponse;
import com.kakaotech.team14backend.common.ApiResponseGenerator;
import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.member.model.Role;
import com.kakaotech.team14backend.inner.member.repository.MemberRepository;
import com.kakaotech.team14backend.jwt.service.TokenService;
import com.kakaotech.team14backend.outer.login.dto.InstagramAccessTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InstagramService {

  private final String GRANT_TYPE = "authorization_code";

  @Value("${instagram.connect-url}")
  private String InstagramConnectURL;

  @Value("${instagram.client-id}")
  private String CLIENT_ID;
  @Value("${instagram.client-secret}")
  private String CLIENT_SECRET;

  @Value("${instagram.redirect-uri}")
  private String REDIRECT_URI;
  @Value("${instagram.token-url}")
  private String TOKEN_URL;
  @Value("${instagram.user-info-url}")
  private String USER_INFO_URL;

  private final MemberRepository memberRepository;
  private final TokenMemoryStorageService tokenMemoryStorageService;
  private final TokenService tokenService;

  public InstagramAccessTokenDto getAccessToken(HttpServletRequest request, String code) {
    String jwt = request.getHeader("accessToken");
    UUID uuid = UUID.randomUUID();
    //자동 콜백 리다이렉션 페이지로의 이동을 고려해 토큰을 서버측에 임시 저장
    //사용자 식별을 위해 uuid로 바꿔 key값 저장
    String uniqueKey = uuid.toString();
    tokenMemoryStorageService.saveJwtToken(uniqueKey,jwt);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    RestTemplate restTemplate = new RestTemplate();
// 요청 파라미터 설정
    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.add("client_id", CLIENT_ID);
    map.add("client_secret", CLIENT_SECRET);
    map.add("grant_type", GRANT_TYPE);
    map.add("redirect_uri", REDIRECT_URI);
    map.add("code", code);

    // Instagram API 호출
    String accessTokenRequestUrl = TOKEN_URL;
    HttpEntity<MultiValueMap<String, String>> requestMap = new HttpEntity<>(map, headers);
    ResponseEntity<Map> responseEntity = restTemplate.postForEntity(accessTokenRequestUrl, requestMap, Map.class);
    // 응답 받은 JSON 데이터 반환
    String accessToken = (String) responseEntity.getBody().get("access_token");
    InstagramAccessTokenDto instagramAccessTokenDto = new InstagramAccessTokenDto(uniqueKey,accessToken);
    return instagramAccessTokenDto;
  }

  public ApiResponse<?> getUserInfo(InstagramAccessTokenDto instagramAccessTokenDto) {

    String accessToken = tokenMemoryStorageService.getJwtToken(instagramAccessTokenDto.getAccessToken());
    String uniqueKey = instagramAccessTokenDto.getUniqueKey();
    RestTemplate restTemplate = new RestTemplate();

    // 응답 받은 JSON 데이터 반환
    String userInfoUrl = USER_INFO_URL + "&access_token=" + accessToken;
    try {
      ResponseEntity<Map> userResponse = restTemplate.exchange(userInfoUrl, HttpMethod.GET, null, Map.class);
      String instaId = (String) userResponse.getBody().get("username");
      String jwt = tokenMemoryStorageService.getJwtToken(uniqueKey);
      DecodedJWT decodedJWT = TokenService.verifyToken(jwt);
      String kakaoId = decodedJWT.getClaim("kakaoId").asString();
      tokenMemoryStorageService.removeJwtToken(uniqueKey);
      Member member = memberRepository.findByKakaoId(kakaoId);
      member.updateInstagram(Role.ROLE_BEGINNER,instaId);
    }catch(NullPointerException e){
      return ApiResponseGenerator.fail("401","잘못된 접근입니다.",HttpStatus.FORBIDDEN);
    }
    return ApiResponseGenerator.success(HttpStatus.OK);
  }

  public void changeUserRole(){

  }
}




