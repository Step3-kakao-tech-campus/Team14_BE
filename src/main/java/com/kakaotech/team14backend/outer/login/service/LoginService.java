package com.kakaotech.team14backend.outer.login.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaotech.team14backend.auth.PrincipalDetails;
import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.member.model.Role;
import com.kakaotech.team14backend.inner.member.model.Status;
import com.kakaotech.team14backend.inner.member.repository.MemberRepository;
import com.kakaotech.team14backend.outer.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class LoginService {
  private final MemberRepository memberRepository;
  private final MemberService memberService;
//  public String getKaKaoAccessToken(String code){
//    //TODO : POST방식으로 key=value 데이터를 요청(카카오로)
//    //httpHeader 오브젝트 생성
//    RestTemplate rt = new RestTemplate();
//    HttpHeaders headers = new HttpHeaders();
//    headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//    //전송할 http 바디의 데이터형태를 전달
//    //HttpHeader와 HttpBody를 하나의 오브젝트에 담기
//    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//    params.add("grant_type", "authorization_code");
//    params.add("client_id", "b51d037debb98e9ae02735887894b407");
//    params.add("redirect_uri", "http://localhost:8080/login/oauth2/code/kakao");
//    params.add("code", code);
//
//    HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
//        new HttpEntity<>(params, headers);
//
//    //Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음.
//    ResponseEntity<String> response = rt.exchange(
//        "https://kauth.kakao.com/oauth/token",
//        HttpMethod.POST,
//        kakaoTokenRequest,
//        String.class
//    );
//
//    ObjectMapper objectMapper = new ObjectMapper();
//    GetKakaoOauth2TokenDTO oAuthToken = null;
//    try {
//      oAuthToken = objectMapper.readValue(response.getBody(), GetKakaoOauth2TokenDTO.class);
//    } catch(JsonProcessingException e){
//      e.printStackTrace();;
//    }
//    return oAuthToken.getAccess_token();
//  }
//
//  public KakaoProfileDTO getKakaoUserInfo(String AccessToken) throws IOException {
//    RestTemplate rt = new RestTemplate();
//    HttpHeaders headers = new HttpHeaders();
//    headers.add("Authorization","Bearer "+ AccessToken);
//    headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//    HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
//        new HttpEntity<>(headers);
//
//    //Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음.
//    ResponseEntity<String> response = rt.exchange(
//        "https://kapi.kakao.com/v2/user/me",
//        HttpMethod.POST,
//        kakaoProfileRequest,
//        String.class
//    );
//    System.out.println(response.getBody());
//    ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);;
//    KakaoProfileDTO kakaoProfileDTO = null;
//    try {
//      kakaoProfileDTO = objectMapper.readValue(response.getBody(), KakaoProfileDTO.class);
//    } catch(JsonProcessingException e){
//      e.printStackTrace();;
//    }
//
//    return kakaoProfileDTO;
//  }
//
//  public void createOrLoginMember(KakaoProfileDTO kakaoProfileDTO){
//    String kakaoId = kakaoProfileDTO.getId();
//    String userName = kakaoProfileDTO.getProperties().getNickname();
//    String profileImage = kakaoProfileDTO.getProperties().getProfileImage();
//    Member memberEntity = memberRepository.findByKakaoId(kakaoId);
//
//    if (memberEntity == null) {
//      memberEntity = memberService.createMember(userName, kakaoId,"none", Role.ROLE_BEGINNER,0L, Status.STATUS_ACTIVE);
//      memberRepository.save(memberEntity);
//    }
//
//    // memberEntity를 기반으로 PrincipalDetails 객체 생성
//    PrincipalDetails principalDetails = new PrincipalDetails(memberEntity);
//
//    // 인증 객체를 생성하고 인증 세션에 저장
//    Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
//    SecurityContextHolder.getContext().setAuthentication(authentication);
//
//  }

}
