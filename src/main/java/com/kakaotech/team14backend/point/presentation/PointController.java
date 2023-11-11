package com.kakaotech.team14backend.point.presentation;

import static com.kakaotech.team14backend.point.domain.GetPointPolicy.USE_100_WHEN_GET_INSTA_ID;

import com.kakaotech.team14backend.common.ApiResponse;
import com.kakaotech.team14backend.common.ApiResponseGenerator;
import com.kakaotech.team14backend.common.MessageCode;
import com.kakaotech.team14backend.member.domain.Member;
import com.kakaotech.team14backend.member.exception.UserNotAuthenticatedException;
import com.kakaotech.team14backend.oauth2.domain.PrincipalDetails;
import com.kakaotech.team14backend.point.application.usecase.UsePoint;
import com.kakaotech.team14backend.point.application.usecase.UsePointForPopularPost;
import com.kakaotech.team14backend.point.dto.UsePointByPopularPostRequestDTO;
import com.kakaotech.team14backend.point.dto.UsePointByPopularPostResponseDTO;
import com.kakaotech.team14backend.point.dto.UsePointByPostRequestDTO;
import com.kakaotech.team14backend.point.dto.UsePointByPostResponseDTO;
import com.kakaotech.team14backend.post.application.usecase.SetPostInstaCount;
import com.kakaotech.team14backend.post.domain.Post;
import com.kakaotech.team14backend.post.exception.PostNotFoundException;
import com.kakaotech.team14backend.post.infrastructure.PostRepository;
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

  private final UsePointForPopularPost usePointForPopularPost;
  private final SetPostInstaCount setPostInstaCount;
  private final UsePoint usePoint;
  private final PostRepository postRepository;

  @ApiOperation(value = "인기 피드 게시물 포인트 사용")
  @PostMapping("/point/popular-post")
  public ApiResponse<ApiResponse.CustomBody<UsePointByPopularPostResponseDTO>> usePointByPopularPost(
      @RequestBody UsePointByPopularPostRequestDTO usePointByPopularPostRequestDTO,
      @AuthenticationPrincipal PrincipalDetails principalDetails) {
    validatePrincipalDetails(principalDetails);

    Long senderId = principalDetails.getMember().getMemberId();
    String instaId = usePointForPopularPost.execute(usePointByPopularPostRequestDTO, senderId);
    UsePointByPopularPostResponseDTO usePointByPopularPostResponseDTO = new UsePointByPopularPostResponseDTO(
        instaId);

    return ApiResponseGenerator.success(usePointByPopularPostResponseDTO, HttpStatus.OK);
  }

  @PostMapping("/point/post")
  public ApiResponse<ApiResponse.CustomBody<UsePointByPostResponseDTO>> usePointByPost(
      @RequestBody UsePointByPostRequestDTO usePointByPostRequestDTO
      , @AuthenticationPrincipal PrincipalDetails principalDetails) {
    validatePrincipalDetails(principalDetails);

    Long postId = usePointByPostRequestDTO.postId();
    Post post = postRepository.findById(postId)
        .orElseThrow(PostNotFoundException::new);

    Member received = post.getMember();
    String instaId = received.getInstaId();

    Long senderId = principalDetails.getMember().getMemberId();
    usePoint.execute(senderId, received.getMemberId(),
        USE_100_WHEN_GET_INSTA_ID.getPoint());
    setPostInstaCount.execute(postId, received.getMemberId());

    UsePointByPostResponseDTO responseDTO = createUsePointResponse(instaId);
    return ApiResponseGenerator.success(responseDTO, HttpStatus.OK);
  }

  private UsePointByPostResponseDTO createUsePointResponse(String instaId) {
    return new UsePointByPostResponseDTO(instaId);
  }

  private void validatePrincipalDetails(PrincipalDetails principalDetails) {
    if (principalDetails == null) {
      throw new UserNotAuthenticatedException(MessageCode.USER_NOT_AUTHENTICATED);
    }
  }
}
