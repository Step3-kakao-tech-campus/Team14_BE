package com.kakaotech.team14backend.outer.point.controller;

import static com.kakaotech.team14backend.inner.point.model.GetPointPolicy.USE_100_WHEN_GET_INSTA_ID;

import com.kakaotech.team14backend.auth.PrincipalDetails;
import com.kakaotech.team14backend.common.ApiResponse;
import com.kakaotech.team14backend.common.ApiResponseGenerator;
import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.point.usecase.UsePointUsecase;
import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.inner.post.repository.PostRepository;
import com.kakaotech.team14backend.outer.point.dto.UsePointByPopularPostRequestDTO;
import com.kakaotech.team14backend.outer.point.dto.UsePointByPopularPostResponseDTO;
import com.kakaotech.team14backend.outer.point.dto.UsePointByPostRequestDTO;
import com.kakaotech.team14backend.outer.point.dto.UsePointByPostResponseDTO;
import com.kakaotech.team14backend.outer.point.service.PointService;
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

  private final UsePointUsecase usePointUsecase;
  private final PostRepository postRepository;

  @ApiOperation(value = "인기 피드 게시물 포인트 사용")
  @PostMapping("/point/popular-post")
  public ApiResponse<ApiResponse.CustomBody<UsePointByPopularPostResponseDTO>> usePointByPopularPost(
      @RequestBody UsePointByPopularPostRequestDTO usePointByPopularPostRequestDTO,
      @AuthenticationPrincipal PrincipalDetails principalDetails) {

    Long senderId = principalDetails.getMember().getMemberId();
    String instaId = pointService.usePointByPopularPost(usePointByPopularPostRequestDTO, senderId);

    UsePointByPopularPostResponseDTO usePointByPopularPostResponseDTO = new UsePointByPopularPostResponseDTO(
        instaId);
    return ApiResponseGenerator.success(usePointByPopularPostResponseDTO, HttpStatus.OK);
  }

  @PostMapping("/point/post")
  public ApiResponse<ApiResponse.CustomBody<UsePointByPostResponseDTO>> usePointByPost(
      @RequestBody UsePointByPostRequestDTO usePointByPostRequestDTO
      , @AuthenticationPrincipal PrincipalDetails principalDetails) {

    // todo: 의존성 뒤집기

    Long postId = usePointByPostRequestDTO.postId();
    Post post = postRepository.findById(postId).orElseThrow();

    Member received = post.getMember();
    String instaId = received.getInstaId();

    Long senderId = principalDetails.getMember().getMemberId();
    usePointUsecase.execute(senderId, received.getMemberId(),
        USE_100_WHEN_GET_INSTA_ID.getPoint());

    UsePointByPostResponseDTO usePointByPostResponseDTO = new UsePointByPostResponseDTO(instaId);
    return ApiResponseGenerator.success(usePointByPostResponseDTO, HttpStatus.OK);
  }
}
