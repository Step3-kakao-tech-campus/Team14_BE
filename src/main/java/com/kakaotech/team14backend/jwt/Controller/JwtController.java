package com.kakaotech.team14backend.jwt.Controller;


import com.auth0.jwt.exceptions.TokenExpiredException;
import com.kakaotech.team14backend.auth.PrincipalDetails;
import com.kakaotech.team14backend.common.ApiResponse;
import com.kakaotech.team14backend.common.ApiResponse.CustomBody;
import com.kakaotech.team14backend.common.ApiResponseGenerator;
import com.kakaotech.team14backend.common.CookieUtils;
import com.kakaotech.team14backend.exception.TokenValidationException;
import com.kakaotech.team14backend.filter.FilterResponseUtils;
import com.kakaotech.team14backend.jwt.dto.ReissueDTO;
import com.kakaotech.team14backend.jwt.dto.TokenDTO;
import com.kakaotech.team14backend.jwt.service.TokenService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class JwtController {

  private final TokenService tokenService;

  @PostMapping("/api/reissue")
  public ApiResponse<?> reissue(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
      String refreshToken = CookieUtils.getCookieValue(request, "RefreshToken");
      ReissueDTO reissueDTO = tokenService.reissueAccessToken(refreshToken);
      response.setContentType("application/json");
      // 새로운 액세스 토큰을 HTTP 헤더에 추가
      response.addHeader("Authorization", reissueDTO.getAccessToken());
      return ApiResponseGenerator.success(HttpStatus.OK);
    } catch (NullPointerException | TokenExpiredException | TokenValidationException e) {
      // 쿠키에 리프레시토큰이 없을 시 혹은 리프레시토큰 검증 실패 시 로그인필요 에러메세지 전달
      return ApiResponseGenerator.fail("401","리프레시토큰이 유효하지 않습니다.",HttpStatus.UNAUTHORIZED);
    }
  }
}

