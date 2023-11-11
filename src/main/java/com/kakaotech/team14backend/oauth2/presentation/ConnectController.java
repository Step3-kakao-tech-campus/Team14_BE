package com.kakaotech.team14backend.oauth2.presentation;

import com.kakaotech.team14backend.oauth2.application.usecase.connect.ConnectInstagram;
import com.kakaotech.team14backend.oauth2.domain.PrincipalDetails;
import com.kakaotech.team14backend.common.ApiResponse;
import com.kakaotech.team14backend.common.ApiResponseGenerator;
import com.kakaotech.team14backend.common.MessageCode;
import com.kakaotech.team14backend.member.exception.UserNotAuthenticatedException;
import com.kakaotech.team14backend.oauth2.dto.GetInstagramCodeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
public class ConnectController {
  private final ConnectInstagram connectInstagram;
  @PostMapping("/api/user/instagram")
  @ResponseBody
  public ApiResponse<?> instagramConnect(HttpServletResponse response,
                                         @RequestBody GetInstagramCodeDTO instagramCode,
                                         @AuthenticationPrincipal PrincipalDetails principalDetails) {
    validatePrincipalDetails(principalDetails);

    String kakaoId = principalDetails.getKakaoId();
    connectInstagram.execute(kakaoId,instagramCode,response);
    return ApiResponseGenerator.success(HttpStatus.OK);
  }

  private void validatePrincipalDetails(PrincipalDetails principalDetails) {
    if (principalDetails == null) {
      throw new UserNotAuthenticatedException(MessageCode.USER_NOT_AUTHENTICATED);
    }
  }
}
