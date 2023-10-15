package com.kakaotech.team14backend.outer.point.controller;

import com.kakaotech.team14backend.common.ApiResponse;
import com.kakaotech.team14backend.common.ApiResponseGenerator;
import com.kakaotech.team14backend.outer.point.dto.UsePointByPopularPostRequestDTO;
import com.kakaotech.team14backend.outer.point.dto.UsePointByPopularPostResponseDTO;
import com.kakaotech.team14backend.outer.point.service.PointService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PointController {

  private final PointService pointService;

  @ApiOperation(value = "인기 피드 게시물 포인트 사용")
  @PostMapping("/point")
  public ApiResponse<ApiResponse.CustomBody<UsePointByPopularPostResponseDTO>> usePointByPopularPost(@RequestBody UsePointByPopularPostRequestDTO usePointByPopularPostRequestDTO) {
    Long memberId = 1L;
    String instaId = pointService.usePointByPopularPost(usePointByPopularPostRequestDTO, memberId);
    UsePointByPopularPostResponseDTO usePointByPopularPostResponseDTO = new UsePointByPopularPostResponseDTO(instaId);
    return ApiResponseGenerator.success(usePointByPopularPostResponseDTO,HttpStatus.OK);
  }
}
