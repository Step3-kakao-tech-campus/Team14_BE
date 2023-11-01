package com.kakaotech.team14backend.outer.login.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaotech.team14backend.auth.PrincipalDetails;
import com.kakaotech.team14backend.common.ApiResponse;
import com.kakaotech.team14backend.common.ApiResponseGenerator;
import com.kakaotech.team14backend.config.RestTemplateConfig;
import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.member.model.Role;
import com.kakaotech.team14backend.inner.member.model.Status;
import com.kakaotech.team14backend.inner.member.repository.MemberRepository;
import com.kakaotech.team14backend.jwt.dto.TokenDTO;
import com.kakaotech.team14backend.jwt.service.TokenService;
import com.kakaotech.team14backend.outer.login.dto.GetKakaoOauth2TokenDTO;
import com.kakaotech.team14backend.outer.login.dto.KakaoProfileDTO;
import com.kakaotech.team14backend.outer.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;

@Service
@Transactional
public class LoginService {
  @Value("${jwt.token-validity-in-seconds-refreshToken}")
  private int cookieAge;

  @Value("${oauth2.kakao.redirect-uri}")
  private  String KAKAO_REDIRECT_URI;
  @Value("${oauth2.kakao.client-id}")
  private String KAKAO_CLIENT_ID;
  @Value("${oauth2.kakao.user-info-uri}")
  private String KAKAO_USER_INFO_URI;
  @Value("${oauth2.kakao.token-uri}")
  private String KAKAO_TOKEN_URI;

  private final RestTemplate proxyRestTemplate;
  private final MemberRepository memberRepository;
  private final MemberService memberService;
  private final TokenService tokenService;

  @Autowired
  public LoginService(
      @Qualifier("proxyRestTemplate") RestTemplate proxyRestTemplate,
      MemberRepository memberRepository,
      MemberService memberService,
      TokenService tokenService) {
    this.proxyRestTemplate = proxyRestTemplate;
    this.memberRepository = memberRepository;
    this.memberService = memberService;
    this.tokenService = tokenService;
  }


  public String getKaKaoAccessToken(String code){
//    Proxy proxy = new Proxy(Proxy.Type.HTTP,new InetSocketAddress("krmp-proxy.9rum.cc", 3128));
//    SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
//    requestFactory.setProxy(proxy);
//    RestTemplate restTemplate = new RestTemplate(requestFactory);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

    //HttpHeader와 HttpBody를 하나의 오브젝트에 담기
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("grant_type", "authorization_code");
    params.add("client_id", KAKAO_CLIENT_ID);
    params.add("redirect_uri", KAKAO_REDIRECT_URI);
    params.add("code", code);

    HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
        new HttpEntity<>(params, headers);

    //Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음.
    ResponseEntity<String> response = proxyRestTemplate.exchange(
        KAKAO_TOKEN_URI,
        HttpMethod.POST,
        kakaoTokenRequest,
        String.class
    );

    ObjectMapper objectMapper = new ObjectMapper();
    GetKakaoOauth2TokenDTO oAuthToken = null;
    try {
      oAuthToken = objectMapper.readValue(response.getBody(), GetKakaoOauth2TokenDTO.class);
    } catch(JsonProcessingException e){
      e.printStackTrace();;
    }
    return oAuthToken.getAccess_token();
  }

  public KakaoProfileDTO getKakaoUserInfo(String AccessToken) throws IOException {
    HttpHeaders headers = new HttpHeaders();

//    Proxy proxy = new Proxy(Proxy.Type.HTTP,new InetSocketAddress("krmp-proxy.9rum.cc", 3128));
//    SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
//    requestFactory.setProxy(proxy);
//    RestTemplate restTemplate = new RestTemplate(requestFactory);

    headers.add("Authorization","Bearer "+ AccessToken);
    headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

    HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
        new HttpEntity<>(headers);

    //Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음.
    ResponseEntity<String> response = proxyRestTemplate.exchange(
        KAKAO_USER_INFO_URI,
        HttpMethod.POST,
        kakaoProfileRequest,
        String.class
    );

    ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);;
    KakaoProfileDTO kakaoProfileDTO = null;
    try {
      kakaoProfileDTO = objectMapper.readValue(response.getBody(), KakaoProfileDTO.class);
    } catch(JsonProcessingException e){
      e.printStackTrace();;
    }

    return kakaoProfileDTO;
  }


  public Authentication createOrLoginMember(KakaoProfileDTO kakaoProfileDTO){
    String kakaoId = kakaoProfileDTO.getId();
    String userName = kakaoProfileDTO.getProperties().getNickname();
    String profileImage = kakaoProfileDTO.getProperties().getProfileImage();
    Member memberEntity = memberRepository.findByKakaoId(kakaoId);

    if (memberEntity == null) {
      memberEntity = memberService.createMember(userName, kakaoId,"none",profileImage, Role.ROLE_BEGINNER,0L, Status.STATUS_ACTIVE);
      memberRepository.save(memberEntity);
    }
    if (memberEntity.getUserStatus().equals(Status.STATUS_INACTIVE)){
      memberService.makeUserActive(memberEntity.getMemberId());
    }

    PrincipalDetails principalDetails = new PrincipalDetails(memberEntity);

    Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authentication);
    return authentication;
  }

  public ApiResponse<?> AuthenticationSuccessHandelr(HttpServletResponse response,KakaoProfileDTO kakaoProfileDTO){
    String kakaoId = kakaoProfileDTO.getId();
    Member member = memberRepository.findByKakaoId(kakaoId);
    TokenDTO tokenDTO = tokenService.createOrUpdateToken(member);

    response.setContentType("application/json");
    // 토큰을 HTTP 헤더에 추가
    response.addHeader("Authorization", tokenDTO.getAccessToken());

    // 리프레시토큰을 쿠키에 저장한다.
    String refreshTokenCookieName = "RefreshToken";
    String refreshTokenCookieValue = tokenDTO.getRefreshToken();
    Cookie refreshTokenCookie = new Cookie("RefreshToken",refreshTokenCookieValue);
    refreshTokenCookie.setHttpOnly(true);  //httponly 옵션 설정
    refreshTokenCookie.setSecure(true); //https 옵션 설정
    refreshTokenCookie.setPath("/"); // 모든 곳에서 쿠키열람이 가능하도록 설정
    refreshTokenCookie.setMaxAge(cookieAge); //쿠키 만료시간 설정
    response.addCookie(refreshTokenCookie);

    //화면에 응답결과 출력
    return ApiResponseGenerator.success(HttpStatus.OK);
  }
}
