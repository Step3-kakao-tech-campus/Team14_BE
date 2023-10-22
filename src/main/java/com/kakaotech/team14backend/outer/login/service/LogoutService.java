package com.kakaotech.team14backend.outer.login.service;

import com.kakaotech.team14backend.auth.PrincipalDetails;
import com.kakaotech.team14backend.common.ApiResponse;
import com.kakaotech.team14backend.common.ApiResponseGenerator;
import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.member.repository.MemberRepository;
import com.kakaotech.team14backend.jwt.repository.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@AllArgsConstructor
public class LogoutService {

  private final MemberRepository memberRepository;
  private final RefreshTokenRepository refreshTokenRepository;

  public ApiResponse<?> logout(HttpServletRequest request, String kakaoId) {
    refreshTokenRepository.deleteRefreshToken(kakaoId);
    return ApiResponseGenerator.success(HttpStatus.OK);
  }
}
