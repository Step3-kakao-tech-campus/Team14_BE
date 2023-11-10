package com.kakaotech.team14backend.member.presentation;

import com.kakaotech.team14backend.oauth2.domain.PrincipalDetails;
import com.kakaotech.team14backend.common.ApiResponse;
import com.kakaotech.team14backend.common.ApiResponseGenerator;
import com.kakaotech.team14backend.common.MessageCode;
import com.kakaotech.team14backend.member.application.FindMemberInfoUsecase;
import com.kakaotech.team14backend.member.application.MemberService;
import com.kakaotech.team14backend.member.dto.GetMemberInfoResponseDTO;
import com.kakaotech.team14backend.member.exception.UserNotAuthenticatedException;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class MemberController {

  private final MemberService memberService;
  private final FindMemberInfoUsecase findMemberInfoUscase;

  @ApiOperation(value = "마이페이지 계정 상세 조회")
  @GetMapping("/user/info")
  public ApiResponse<ApiResponse.CustomBody<GetMemberInfoResponseDTO>> getMyPageInfo(
      @AuthenticationPrincipal PrincipalDetails principalDetails) {
    validatePrincipalDetails(principalDetails);

    Long memberId = principalDetails.getMember().getMemberId();
    GetMemberInfoResponseDTO myPageInfo = findMemberInfoUscase.getMyPageInfo(memberId);
    return ApiResponseGenerator.success(myPageInfo, HttpStatus.OK);
  }

  @ApiOperation(value = "계정 탈퇴")
  @DeleteMapping("/user/account")
  public ApiResponse<?> deleteAccount(@AuthenticationPrincipal PrincipalDetails principalDetails) {
    validatePrincipalDetails(principalDetails);

    Long memberId = principalDetails.getMember().getMemberId();
    memberService.deleteAccount(memberId);
    return ApiResponseGenerator.success(HttpStatus.OK);
  }

  private void validatePrincipalDetails(PrincipalDetails principalDetails) {
    if (principalDetails == null) {
      throw new UserNotAuthenticatedException(MessageCode.USER_NOT_AUTHENTICATED);
    }
  }
}

