package com.kakaotech.team14backend.outer.post.controller;

import com.kakaotech.team14backend.auth.PrincipalDetails;
import com.kakaotech.team14backend.common.ApiResponse;
import com.kakaotech.team14backend.common.ApiResponse.CustomBody;
import com.kakaotech.team14backend.common.ApiResponseGenerator;
import com.kakaotech.team14backend.common.MessageCode;
import com.kakaotech.team14backend.exception.Exception400;
import com.kakaotech.team14backend.exception.MaxLevelSizeException;
import com.kakaotech.team14backend.outer.post.dto.GetHomePostListResponseDTO;
import com.kakaotech.team14backend.outer.post.dto.GetMyPostResponseDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPersonalPostListResponseDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPopularPostListRequestDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPopularPostListResponseDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPopularPostResponseDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPostDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPostResponseDTO;
import com.kakaotech.team14backend.outer.post.dto.SetPostLikeDTO;
import com.kakaotech.team14backend.outer.post.dto.SetPostLikeResponseDTO;
import com.kakaotech.team14backend.outer.post.dto.UploadPostDTO;
import com.kakaotech.team14backend.outer.post.dto.UploadPostRequestDTO;
import com.kakaotech.team14backend.outer.post.service.PostService;
import io.swagger.annotations.ApiOperation;
import java.util.HashMap;
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

  private final PostService postService;

  @GetMapping("/post/user")// 유저가 올린 게시물 조회
  public ApiResponse<CustomBody<GetPersonalPostListResponseDTO>> getPersonalPostList(
      @AuthenticationPrincipal PrincipalDetails principalDetails,
      @RequestParam(value = "lastPostId", required = false) Long lastPostId,
      @RequestParam(defaultValue = "10") int size) {
    Long memberId = principalDetails.getMember().getMemberId();
    GetPersonalPostListResponseDTO getPostListResponseDTO = postService.getPersonalPostList(
        memberId, lastPostId, size);
    return ApiResponseGenerator.success(getPostListResponseDTO, HttpStatus.OK);
  }

  @ApiOperation(value = "홈 피드의 게시물 조회", notes = "마지막 게시물의 id를 받아서 그 이후의 게시물을 조회한다. lastPostId가 없다면 첫 게시물을 조회한다")
  @GetMapping("/post")
  public ApiResponse<CustomBody<GetHomePostListResponseDTO>> getPosts(
      @AuthenticationPrincipal PrincipalDetails principalDetails
      , @RequestParam(value = "lastPostId", required = false) Long lastPostId,
      @RequestParam(defaultValue = "10") int size) {

    if (size <= 0) {
      throw new Exception400("Size parameter must be greater than 0");
    }
    if (lastPostId != null && lastPostId < 0) {
      throw new Exception400("lastPostId parameter must be greater than 0");
    }
    if (principalDetails == null) {
      GetHomePostListResponseDTO getPostListResponseDTO = postService.getNonAuthenticatedPostList(
          lastPostId, size);
      return ApiResponseGenerator.success(getPostListResponseDTO, HttpStatus.OK);
    }

    Long memberId = principalDetails.getMember().getMemberId();
    GetHomePostListResponseDTO getPostListResponseDTO = postService.getAuthenticatedPostList(
        lastPostId, size,
        memberId);
    return ApiResponseGenerator.success(getPostListResponseDTO, HttpStatus.OK);
  }

  @ApiOperation(value = "게시물 업로드", notes = "이미지와 닉네임 해시태그등을 업로드한다.")
  @PostMapping(value = "/post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ApiResponse<ApiResponse.CustomBody<Void>> uploadPost(
      @ModelAttribute UploadPostRequestDTO uploadPostRequestDTO,
      @AuthenticationPrincipal PrincipalDetails principalDetails){
    UploadPostDTO uploadPostDTO = new UploadPostDTO(principalDetails.getMember(),
        uploadPostRequestDTO);
    postService.uploadPost(uploadPostDTO);

    return ApiResponseGenerator.success(HttpStatus.CREATED);
  }

  @GetMapping("/post/{postId}/user")
  public ApiResponse<ApiResponse.CustomBody<GetMyPostResponseDTO>> getMyPost(
      @AuthenticationPrincipal PrincipalDetails principalDetails,
      @PathVariable("postId") Long postId) {
    Long memberId = principalDetails.getMember().getMemberId();
    if (postId == null) {
      throw new Exception400("postId parameter must be not null");
    }
    GetMyPostResponseDTO getMyPostResponseDTO = postService.getMyPost(memberId, postId);
    System.out.println("/post/{postId}/user Response: " + getMyPostResponseDTO);
    return ApiResponseGenerator.success(getMyPostResponseDTO, HttpStatus.OK);
  }

  @GetMapping("/post/{postId}")
  public ApiResponse<ApiResponse.CustomBody<GetPostResponseDTO>> getPost(
      @PathVariable("postId") Long postId,
      @AuthenticationPrincipal PrincipalDetails principalDetails) {
    GetPostDTO getPostDTO = new GetPostDTO(postId, principalDetails.getMember().getMemberId());
    GetPostResponseDTO getPostResponseDTO = postService.getPost(getPostDTO);
    return ApiResponseGenerator.success(getPostResponseDTO, HttpStatus.OK);
  }

  @ApiOperation(value = "인기 피드 게시물 상세 조회")
  @GetMapping("/popular-post/{postId}")
  public ApiResponse<ApiResponse.CustomBody<GetPopularPostResponseDTO>> getPopularPost(
      @PathVariable("postId") Long postId,
      @AuthenticationPrincipal PrincipalDetails principalDetails) {
    GetPostDTO getPostDTO = new GetPostDTO(postId, principalDetails.getMember().getMemberId());
    GetPopularPostResponseDTO getPopularPostResponseDTO = postService.getPopularPost(getPostDTO);
    return ApiResponseGenerator.success(getPopularPostResponseDTO, HttpStatus.OK);
  }

  @ApiOperation(value = "인기 피드 게시물 조회", notes = "레벨당 게시물이 몇개가 필요한 지를 받아, 해당 레벨별 게시물들을 반환한다.")
  @GetMapping("/popular-post")
  public ApiResponse<ApiResponse.CustomBody<GetPopularPostListResponseDTO>> getPopularPostList(
      @RequestParam Integer level1, @RequestParam Integer level2, @RequestParam Integer level3) {

    if (level1 >= 10 || level2 >= 10 || level3 >= 10) {
      throw new MaxLevelSizeException(MessageCode.LEVEL_SIZE_SMALLER_THAN_20);
    }

    Map<Integer, Integer> levelSize = new HashMap<>();
    levelSize.put(1, level1);
    levelSize.put(2, level2);
    levelSize.put(3, level3);

    GetPopularPostListRequestDTO getPopularPostListRequestDTO = new GetPopularPostListRequestDTO(
        levelSize);

    GetPopularPostListResponseDTO popularPostList = postService.getPopularPostList(
        getPopularPostListRequestDTO);
    return ApiResponseGenerator.success(popularPostList, HttpStatus.OK);
  }

  @ApiOperation(value = "게시물 좋아요(추천 수)", notes = "게시물 좋아요를 누른다, 1인당 1개의 게시물에 1번만 좋아요를 누를 수 있다")
  @PostMapping("/post/{postId}/like")
  public ApiResponse<ApiResponse.CustomBody<SetPostLikeResponseDTO>> setPostLike(
      @PathVariable("postId") Long postId,
      @AuthenticationPrincipal PrincipalDetails principalDetails) {
    Long memberId = principalDetails.getMember().getMemberId();
    SetPostLikeDTO setPostLikeDTO = new SetPostLikeDTO(postId, memberId);
    SetPostLikeResponseDTO setPostLikeResponseDTO = postService.setPostLike(setPostLikeDTO);
    System.out.println("/post/{postId}/like Response: " + setPostLikeResponseDTO);
    return ApiResponseGenerator.success(setPostLikeResponseDTO, HttpStatus.OK);
  }

}
