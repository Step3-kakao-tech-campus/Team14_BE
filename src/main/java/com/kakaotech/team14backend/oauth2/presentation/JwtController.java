package com.kakaotech.team14backend.oauth2.presentation;


import com.kakaotech.team14backend.common.ApiResponse;
import com.kakaotech.team14backend.common.ApiResponseGenerator;
import com.kakaotech.team14backend.common.CookieUtils;
import com.kakaotech.team14backend.oauth2.application.usecase.token.ReissueAccessTokenService;
import com.kakaotech.team14backend.oauth2.dto.SetReissueDTO;
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

  private  final ReissueAccessTokenService reissueAccessToken;

  @PostMapping("/api/reissue")
  public ApiResponse<?> reissue(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String refreshToken = CookieUtils.getCookieValue(request, "RefreshToken");
    SetReissueDTO setReissueDTO = reissueAccessToken.execute(refreshToken);
    response.setContentType("application/json");
    // 새로운 액세스 토큰을 HTTP 헤더에 추가
    response.addHeader("Authorization", setReissueDTO.getAccessToken());
    return ApiResponseGenerator.success(HttpStatus.OK);
  }
}

