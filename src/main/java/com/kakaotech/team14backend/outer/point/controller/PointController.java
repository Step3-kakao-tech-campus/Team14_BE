package com.kakaotech.team14backend.outer.point.controller;

import com.kakaotech.team14backend.auth.PrincipalDetails;
import com.kakaotech.team14backend.common.ApiResponse;
import com.kakaotech.team14backend.common.ApiResponseGenerator;
import com.kakaotech.team14backend.outer.point.dto.UsePointByPopularPostRequestDTO;
import com.kakaotech.team14backend.outer.point.dto.UsePointByPopularPostResponseDTO;
import com.kakaotech.team14backend.outer.point.dto.UsePointByPostRequestDTO;
import com.kakaotech.team14backend.outer.point.dto.UsePointByPostResponseDTO;
import com.kakaotech.team14backend.outer.point.service.PointService;
import com.kakaotech.team14backend.outer.point.usecase.usePointByPostUsecase;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PointController {

  private final PointService pointService;
  private final usePointByPostUsecase usePointByPostUsecase;

  @ApiOperation(value = "인기 피드 게시물 포인트 사용")
  @PostMapping("/point/popular-post")
  public ApiResponse<ApiResponse.CustomBody<UsePointByPopularPostResponseDTO>> usePointByPopularPost(@RequestBody UsePointByPopularPostRequestDTO usePointByPopularPostRequestDTO, @AuthenticationPrincipal PrincipalDetails principalDetails) {
    String instaId = pointService.usePointByPopularPost(usePointByPopularPostRequestDTO, principalDetails.getMember().getMemberId());
    UsePointByPopularPostResponseDTO usePointByPopularPostResponseDTO = new UsePointByPopularPostResponseDTO(instaId);
    return ApiResponseGenerator.success(usePointByPopularPostResponseDTO,HttpStatus.OK);
  @PostMapping("/point/post")
  public ApiResponse<ApiResponse.CustomBody<UsePointByPostResponseDTO>> usePointByPost(
      @RequestBody UsePointByPostRequestDTO usePointByPostRequestDTO,
      @AuthenticationPrincipal PrincipalDetails principalDetails) {
    String instaId = usePointByPostUsecase.getInstaId(usePointByPostRequestDTO,
        principalDetails.getMember().getMemberId());
    UsePointByPostResponseDTO usePointByPostResponseDTO = new UsePointByPostResponseDTO(instaId);
    return ApiResponseGenerator.success(usePointByPostResponseDTO, HttpStatus.OK);
  }
}
