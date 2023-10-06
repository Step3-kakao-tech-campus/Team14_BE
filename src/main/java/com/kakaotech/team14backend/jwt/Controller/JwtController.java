package com.kakaotech.team14backend.jwt.Controller;


import com.kakaotech.team14backend.common.ApiResponse;
import com.kakaotech.team14backend.common.ApiResponse.CustomBody;
import com.kakaotech.team14backend.common.ApiResponseGenerator;
import com.kakaotech.team14backend.jwt.dto.ReissueDTO;
import com.kakaotech.team14backend.jwt.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class JwtController {

  private final TokenService tokenService;


  @PostMapping("/api/reissue")
  public ApiResponse<?> reissue(HttpServletRequest request) {
    String refreshToken = request.getHeader(TokenService.HEADER);
    System.out.println(refreshToken);
    try {
      ReissueDTO reissueDTO = tokenService.reissueAccessToken(refreshToken);
      return ApiResponseGenerator.success(reissueDTO, HttpStatus.OK);
    } catch (Exception e) {
      return ApiResponseGenerator.fail("401", "만료 혹은 잘못된 토큰",HttpStatus.FORBIDDEN);
    }
  }
}

