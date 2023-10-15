package com.kakaotech.team14backend.outer.point.controller;

import com.kakaotech.team14backend.common.ApiResponse;
import com.kakaotech.team14backend.common.ApiResponseGenerator;
import com.kakaotech.team14backend.outer.point.dto.UsePointByPopularPostRequestDTO;
import com.kakaotech.team14backend.outer.point.service.PointService;
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
  @PostMapping("/point")
  public ApiResponse<ApiResponse.CustomBody<Void>> usePointByPopularPost(@RequestBody UsePointByPopularPostRequestDTO usePointByPopularPostRequestDTO) {
    Long memberId = 1L;
    pointService.usePointByPopularPost(usePointByPopularPostRequestDTO, memberId);
    return ApiResponseGenerator.success(HttpStatus.OK);
  }
}
