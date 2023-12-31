package com.kakaotech.team14backend.post.presentation;

import com.kakaotech.team14backend.common.ApiResponse;
import com.kakaotech.team14backend.common.ApiResponse.CustomBody;
import com.kakaotech.team14backend.common.ApiResponseGenerator;
import com.kakaotech.team14backend.common.MessageCode;
import com.kakaotech.team14backend.member.domain.Member;
import com.kakaotech.team14backend.member.exception.UserNotAuthenticatedException;
import com.kakaotech.team14backend.oauth2.domain.PrincipalDetails;
import com.kakaotech.team14backend.post.application.usecase.GetHomePostList;
import com.kakaotech.team14backend.post.application.usecase.GetMyPost;
import com.kakaotech.team14backend.post.application.usecase.GetMyPostList;
import com.kakaotech.team14backend.post.application.usecase.GetPopularPost;
import com.kakaotech.team14backend.post.application.usecase.GetPopularPosts;
import com.kakaotech.team14backend.post.application.usecase.GetPostUsecase;
import com.kakaotech.team14backend.post.application.usecase.SetPostLikeUsecase;
import com.kakaotech.team14backend.post.application.usecase.UploadPost;
import com.kakaotech.team14backend.post.dto.GetHomePostListResponseDTO;
import com.kakaotech.team14backend.post.dto.GetMyPostResponseDTO;
import com.kakaotech.team14backend.post.dto.GetPersonalPostListResponseDTO;
import com.kakaotech.team14backend.post.dto.GetPopularPostListRequestDTO;
import com.kakaotech.team14backend.post.dto.GetPopularPostListResponseDTO;
import com.kakaotech.team14backend.post.dto.GetPopularPostResponseDTO;
import com.kakaotech.team14backend.post.dto.GetPostDTO;
import com.kakaotech.team14backend.post.dto.GetPostResponseDTO;
import com.kakaotech.team14backend.post.dto.SetPostLikeDTO;
import com.kakaotech.team14backend.post.dto.SetPostLikeResponseDTO;
import com.kakaotech.team14backend.post.dto.UploadPostDTO;
import com.kakaotech.team14backend.post.dto.UploadPostRequestDTO;
import com.kakaotech.team14backend.post.exception.LastPostIdParameterException;
import com.kakaotech.team14backend.post.exception.MaxLevelSizeException;
import com.kakaotech.team14backend.post.exception.SizeParameterException;
import io.swagger.annotations.ApiOperation;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

  private final GetPopularPosts getPopularPosts;
  private final GetPopularPost getPopularPost;
  private final GetMyPostList getMyPostList;
  private final GetHomePostList getHomePostList;
  private final SetPostLikeUsecase setPostLikeUsecase;
  private final UploadPost uploadPost;
  private final GetMyPost getMyPost;
  private final GetPostUsecase getPostUsecase;


  @ApiOperation(value = "유저가 올린 게시물 조회", notes = "마지막 게시물의 id를 받아서 그 이후의 게시물을 조회한다. lastPostId가 없다면 첫 게시물을 조회한다")
  @GetMapping("/post/user")// 유저가 올린 게시물 조회
  public ApiResponse<CustomBody<GetPersonalPostListResponseDTO>> getPersonalPostList(
      @AuthenticationPrincipal PrincipalDetails principalDetails,
      @RequestParam(value = "lastPostId", required = false) Long lastPostId,
      @RequestParam(defaultValue = "10") int size) {

    validatePrincipalDetails(principalDetails);
    validateParameters(size, lastPostId);
    Long memberId = principalDetails.getMemberId();
    GetPersonalPostListResponseDTO getPostListResponseDTO = getMyPostList.execute(
        memberId, lastPostId, size);
    return ApiResponseGenerator.success(getPostListResponseDTO, HttpStatus.OK);
  }

  @ApiOperation(value = "홈 피드의 게시물 조회", notes = "마지막 게시물의 id를 받아서 그 이후의 게시물을 조회한다. lastPostId가 없다면 첫 게시물을 조회한다")
  @GetMapping("/post")
  public ApiResponse<CustomBody<GetHomePostListResponseDTO>> getPosts(
      @AuthenticationPrincipal PrincipalDetails principalDetails,
      @RequestParam(value = "lastPostId", required = false) Long lastPostId,
      @RequestParam(defaultValue = "10") int size) {

    validateParameters(size, lastPostId);

    Long memberId = (principalDetails == null) ? null : principalDetails.getMemberId();
    GetHomePostListResponseDTO getPostListResponseDTO = getHomePostList.execute(lastPostId,
        size, memberId);
    return ApiResponseGenerator.success(getPostListResponseDTO, HttpStatus.OK);
  }

  @ApiOperation(value = "게시물 업로드", notes = "이미지와 닉네임 해시태그등을 업로드한다.")
  @PostMapping(value = "/post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ApiResponse<ApiResponse.CustomBody<Void>> uploadPost(
      @ModelAttribute UploadPostRequestDTO uploadPostRequestDTO,
      @AuthenticationPrincipal PrincipalDetails principalDetails) {
    Member member = principalDetails.getMember();
    UploadPostDTO uploadPostDTO = new UploadPostDTO(member, uploadPostRequestDTO);
    uploadPost.execute(uploadPostDTO);

    return ApiResponseGenerator.success(HttpStatus.CREATED);
  }

  @ApiOperation(value = "내가 올린 게시물 상세 조회", notes = "내가 올린 게시물을 상세 조회한다")
  @GetMapping("/post/{postId}/user")
  public ApiResponse<ApiResponse.CustomBody<GetMyPostResponseDTO>> getMyPost(
      @AuthenticationPrincipal PrincipalDetails principalDetails,
      @PathVariable("postId") Long postId) {
    validatePrincipalDetails(principalDetails);

    Long memberId = principalDetails.getMemberId();

    GetPostDTO getPostDTO = new GetPostDTO(postId, memberId);
    GetMyPostResponseDTO getMyPostResponseDTO = getMyPost.execute(getPostDTO);
    return ApiResponseGenerator.success(getMyPostResponseDTO, HttpStatus.OK);
  }

  @ApiOperation(value = "게시물 상세 조회", notes = "게시물을 상세 조회한다")
  @GetMapping("/post/{postId}")
  public ApiResponse<ApiResponse.CustomBody<GetPostResponseDTO>> getPost(
      @PathVariable("postId") Long postId,
      @AuthenticationPrincipal PrincipalDetails principalDetails) {

    validatePrincipalDetails(principalDetails);
    GetPostDTO getPostDTO = new GetPostDTO(postId, principalDetails.getMemberId());
    GetPostResponseDTO getPostResponseDTO = getPostUsecase.execute(getPostDTO);
    return ApiResponseGenerator.success(getPostResponseDTO, HttpStatus.OK);
  }

  @ApiOperation(value = "인기 피드 게시물 상세 조회")
  @GetMapping("/popular-post/{postId}")
  public ApiResponse<ApiResponse.CustomBody<GetPopularPostResponseDTO>> getPopularPost(
      @PathVariable("postId") Long postId,
      @AuthenticationPrincipal PrincipalDetails principalDetails) {

    validatePrincipalDetails(principalDetails);
    GetPostDTO getPostDTO = new GetPostDTO(postId, principalDetails.getMemberId());
    GetPopularPostResponseDTO getPopularPostResponseDTO = getPopularPost.execute(getPostDTO);
    return ApiResponseGenerator.success(getPopularPostResponseDTO, HttpStatus.OK);
  }

  @ApiOperation(value = "인기 피드 게시물 조회", notes = "레벨당 게시물이 몇개가 필요한 지를 받아, 해당 레벨별 게시물들을 반환한다.")
  @GetMapping("/popular-post")
  public ApiResponse<ApiResponse.CustomBody<GetPopularPostListResponseDTO>> getPopularPostList(
      @RequestParam Integer level3, @RequestParam Integer level2, @RequestParam Integer level1) {

    validateLevels(level3, level2, level1);
    GetPopularPostListRequestDTO getPopularPostListRequestDTO = getGetPopularPostListRequestDTO(
        level3, level2, level1);
    GetPopularPostListResponseDTO popularPostList = getPopularPosts.execute(
        getPopularPostListRequestDTO);
    return ApiResponseGenerator.success(popularPostList, HttpStatus.OK);
  }

  private GetPopularPostListRequestDTO getGetPopularPostListRequestDTO(Integer level3,
      Integer level2, Integer level1) {

    Map<Integer, Integer> levelSize = new LinkedHashMap<>();
    levelSize.put(3, level3);
    levelSize.put(2, level2);
    levelSize.put(1, level1);
    GetPopularPostListRequestDTO getPopularPostListRequestDTO = new GetPopularPostListRequestDTO(
        levelSize);
    return getPopularPostListRequestDTO;
  }

  private void validateLevels(Integer level3, Integer level2, Integer level1) {
    if (level1 >= 10 || level2 >= 10 || level3 >= 10) {
      throw new MaxLevelSizeException();
    }
  }

  @ApiOperation(value = "게시물 좋아요(추천 수)", notes = "게시물 좋아요를 누른다, 1인당 1개의 게시물에 1번만 좋아요를 누를 수 있다")
  @PostMapping("/post/{postId}/like")
  public ApiResponse<ApiResponse.CustomBody<SetPostLikeResponseDTO>> setPostLike(
      @PathVariable("postId") Long postId,
      @AuthenticationPrincipal PrincipalDetails principalDetails) {

    validatePrincipalDetails(principalDetails);

    Long memberId = principalDetails.getMemberId();
    SetPostLikeDTO setPostLikeDTO = new SetPostLikeDTO(postId, memberId);
    SetPostLikeResponseDTO setPostLikeResponseDTO = setPostLikeUsecase.execute(setPostLikeDTO);
    return ApiResponseGenerator.success(setPostLikeResponseDTO, HttpStatus.OK);
  }

  private void validateParameters(int size, Long lastPostId) {
    if (size <= 0) {
      throw new SizeParameterException(MessageCode.INVALID_SIZE_PARAMETER);
    }
    if (lastPostId != null && lastPostId < 0) {
      throw new LastPostIdParameterException(MessageCode.INVALID_LAST_POST_ID_PARAMETER);
    }
  }

  private void validatePrincipalDetails(PrincipalDetails principalDetails) {
    if (principalDetails == null) {
      throw new UserNotAuthenticatedException(MessageCode.USER_NOT_AUTHENTICATED);
    }
  }
}
