package com.kakaotech.team14backend.auth;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaotech.team14backend.common.ApiResponse;
import com.kakaotech.team14backend.common.ApiResponseGenerator;
import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.member.repository.MemberRepository;
import com.kakaotech.team14backend.jwt.JwtProvider;
import com.kakaotech.team14backend.outer.login.dto.TokenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class AuthenticationSuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {

  private final MemberRepository memberRepository;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
    PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
    String kakaoId = Long.toString(principalDetails.getAttribute("id"));
    Member member = memberRepository.findByKakaoId(kakaoId);
    String accessToken = JwtProvider.create(member);
    TokenDTO tokenDTO = new TokenDTO();
    tokenDTO.setAccessToken(accessToken);
//    ApiResponse<?> apiResponse = new ApiResponse<>(new ApiResponse.CustomBody(true,"accessToken:" + jwt,null), HttpStatus.OK);
    ApiResponse<?> apiResponse = ApiResponseGenerator.success(tokenDTO,HttpStatus.OK);
    response.setContentType("application/json");
    response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse));
  }
}

