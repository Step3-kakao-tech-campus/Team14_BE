package com.kakaotech.team14backend.jwt.Controller;


import com.kakaotech.team14backend.auth.PrincipalDetails;
import com.kakaotech.team14backend.common.ApiResponse;
import com.kakaotech.team14backend.common.ApiResponse.CustomBody;
import com.kakaotech.team14backend.common.ApiResponseGenerator;
import com.kakaotech.team14backend.exception.TokenValidationException;
import com.kakaotech.team14backend.jwt.dto.ReissueDTO;
import com.kakaotech.team14backend.jwt.dto.TokenDTO;
import com.kakaotech.team14backend.jwt.service.TokenService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class JwtController {

  private final TokenService tokenService;

  @PostMapping("/api/reissue")
  public ApiResponse<?> reissue(HttpServletRequest request) {
    String refreshToken = request.getHeader(TokenService.HEADER);
    try {
      ReissueDTO reissueDTO = tokenService.reissueAccessToken(refreshToken);
      return ApiResponseGenerator.success(reissueDTO, HttpStatus.OK);
    } catch (TokenValidationException tokenValidationException) {
      return ApiResponseGenerator.fail(tokenValidationException.messageCode.getCode(),tokenValidationException.messageCode.getValue(),HttpStatus.FORBIDDEN);
    }
  }
}

