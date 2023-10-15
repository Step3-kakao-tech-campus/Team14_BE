package com.kakaotech.team14backend.outer.member.controller;

import com.kakaotech.team14backend.common.ApiResponse;
import com.kakaotech.team14backend.common.ApiResponseGenerator;
import com.kakaotech.team14backend.outer.member.dto.MyPageResponseDTO;
import com.kakaotech.team14backend.outer.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class MyPageController {

  private MemberService memberService;

  @GetMapping("/user/info")
  public ApiResponse<ApiResponse.CustomBody<MyPageResponseDTO>> getMyPageInfo(String kakaoId) {
    MyPageResponseDTO myPageInfo = memberService.getMyPageInfo(kakaoId);
    return ApiResponseGenerator.success(myPageInfo, HttpStatus.OK);
  }
}

