package com.kakaotech.team14backend.outer.member.controller;

import com.kakaotech.team14backend.auth.PrincipalDetails;
import com.kakaotech.team14backend.common.ApiResponse;
import com.kakaotech.team14backend.common.ApiResponseGenerator;
import com.kakaotech.team14backend.inner.member.service.FindMemberInfoService;
import com.kakaotech.team14backend.outer.member.dto.GetMemberInfoResponseDTO;
import com.kakaotech.team14backend.outer.member.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class MemberController {

  private final MemberService memberService;
  private final FindMemberInfoService findMemberInfoService;

  @ApiOperation(value = "마이페이지 계정 상세 조회")
  @GetMapping("/user/info")
  public ApiResponse<ApiResponse.CustomBody<GetMemberInfoResponseDTO>> getMyPageInfo(
      @AuthenticationPrincipal PrincipalDetails principalDetails) {
    Long memberId = principalDetails.getMember().getMemberId();
    GetMemberInfoResponseDTO myPageInfo = memberService.getMyPageInfo(memberId);
    GetMemberInfoResponseDTO myPageInfo = findMemberInfoService.getMyPageInfo(memberId);
    return ApiResponseGenerator.success(myPageInfo, HttpStatus.OK);
  }

  @ApiOperation(value = "계정 탈퇴")
  @DeleteMapping("/user/account")
  public ApiResponse<?> deleteAccount(@AuthenticationPrincipal PrincipalDetails principalDetails) {
    Long memberId = principalDetails.getMember().getMemberId();
    memberService.deleteAccount(memberId);
    return ApiResponseGenerator.success(HttpStatus.OK);
  }
}

