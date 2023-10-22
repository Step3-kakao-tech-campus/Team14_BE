package com.kakaotech.team14backend.outer.member.controller;

import com.kakaotech.team14backend.common.ApiResponse;
import com.kakaotech.team14backend.common.ApiResponseGenerator;
import com.kakaotech.team14backend.outer.member.dto.GetMemberInfoResponseDTO;
import com.kakaotech.team14backend.outer.member.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class MemberController {

  private MemberService memberService;

  @ApiOperation(value = "마이페이지 계정 상세 조회")
  @GetMapping("/user/info")
  public ApiResponse<ApiResponse.CustomBody<GetMemberInfoResponseDTO>> getMyPageInfo(String kakaoId) {
    GetMemberInfoResponseDTO myPageInfo = memberService.getMyPageInfo(kakaoId);
    return ApiResponseGenerator.success(myPageInfo, HttpStatus.OK);
  }
}

