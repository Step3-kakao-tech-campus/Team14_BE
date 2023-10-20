package com.kakaotech.team14backend.outer.post.controller;

import com.kakaotech.team14backend.common.ApiResponse;
import com.kakaotech.team14backend.common.ApiResponse.CustomBody;
import com.kakaotech.team14backend.common.ApiResponseGenerator;
import com.kakaotech.team14backend.exception.Exception400;
import com.kakaotech.team14backend.outer.post.dto.GetPersonalPostListResponseDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPopularPostListRequestDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPopularPostListResponseDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPostDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPostListResponseDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPostResponseDTO;
import com.kakaotech.team14backend.outer.post.dto.SetPostLikeDTO;
import com.kakaotech.team14backend.outer.post.dto.SetPostLikeResponseDTO;
import com.kakaotech.team14backend.outer.post.dto.UploadPostDTO;
import com.kakaotech.team14backend.outer.post.dto.UploadPostRequestDTO;
import com.kakaotech.team14backend.outer.post.service.PostService;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

  private final PostService postService;

  @GetMapping("/post/user")// 유저가 올린 게시물 조회
  public ApiResponse<CustomBody<GetPersonalPostListResponseDTO>> getPersonalPostList(
      @RequestParam("userId") Long userId,
      @RequestParam(value = "lastPostId", required = false) Long lastPostId,
      @RequestParam(defaultValue = "10") int size) {

    GetPersonalPostListResponseDTO getPostListResponseDTO = postService.getPersonalPostList(userId,
        lastPostId, size);
    return ApiResponseGenerator.success(getPostListResponseDTO, HttpStatus.OK);
  }

  @ApiOperation(value = "홈 피드의 게시물 조회", notes = "마지막 게시물의 id를 받아서 그 이후의 게시물을 조회한다. lastPostId가 없다면 첫 게시물을 조회한다")
  @GetMapping("/post")
  public ApiResponse<CustomBody<GetPostListResponseDTO>> getPosts(
      @RequestParam(value = "lastPostId", required = false) Long lastPostId,
      @RequestParam(defaultValue = "10") int size) {

    if (size <= 0) {
      throw new Exception400("Size parameter must be greater than 0");
    }
    if (lastPostId != null && lastPostId < 0) {
      throw new Exception400("lastPostId parameter must be greater than 0");
    }
    GetPostListResponseDTO getPostListResponseDTO = postService.getPostList(lastPostId, size);
    return ApiResponseGenerator.success(getPostListResponseDTO, HttpStatus.OK);
  }

  @ApiOperation(value = "게시물 업로드", notes = "이미지와 닉네임 해시태그등을 업로드한다.")
  @PostMapping("/post")
  public ApiResponse<ApiResponse.CustomBody<Void>> uploadPost(@RequestPart MultipartFile image,
      @RequestPart UploadPostRequestDTO uploadPostRequestDTO) throws IOException {

    Long memberId = 1L;
    UploadPostDTO uploadPostDTO = new UploadPostDTO(memberId, image, uploadPostRequestDTO);
    postService.uploadPost(uploadPostDTO);
    return ApiResponseGenerator.success(HttpStatus.CREATED);
  }

  @GetMapping("/post/{postId}")
  public ApiResponse<ApiResponse.CustomBody<GetPostResponseDTO>> getPost(
      @PathVariable("postId") Long postId) {
    Long memberId = 1L;
    GetPostDTO getPostDTO = new GetPostDTO(postId, memberId);
    GetPostResponseDTO getPostResponseDTO = postService.getPost(getPostDTO);
    return ApiResponseGenerator.success(getPostResponseDTO, HttpStatus.OK);
  }

  @ApiOperation(value = "인기 피드 게시물 상세 조회")
  @GetMapping("/popular-post/{postId}")
  public ApiResponse<ApiResponse.CustomBody<GetPostResponseDTO>> getPopularPost(
      @PathVariable("postId") Long postId) {
    Long memberId = 1L;
    GetPostDTO getPostDTO = new GetPostDTO(postId, memberId);
    GetPostResponseDTO getPostResponseDTO = postService.getPopularPost(getPostDTO);
    return ApiResponseGenerator.success(getPostResponseDTO, HttpStatus.OK);
  }

  @ApiOperation(value = "인기 피드 게시물 조회", notes = "레벨당 게시물이 몇개가 필요한 지를 받아, 해당 레벨별 게시물들을 반환한다.")
  @GetMapping("/popular-post")
  public ApiResponse<ApiResponse.CustomBody<GetPopularPostListResponseDTO>> getPopularPostList(
      @RequestParam Integer level1, @RequestParam Integer level2, @RequestParam Integer level3) {

    if (level1 >= 20 | level2 >= 20 | level3 >= 20) {
      throw new Exception400("Level size must be smaller than 20");
    }

    Map<Integer, Integer> levelSize = new HashMap<>();
    levelSize.put(1, level1);
    levelSize.put(2, level2);
    levelSize.put(3, level3);

    GetPopularPostListRequestDTO getPopularPostListRequestDTO = new GetPopularPostListRequestDTO(
        levelSize);

    GetPopularPostListResponseDTO popularPostList = postService.getPopularPostList(getPopularPostListRequestDTO);
    return ApiResponseGenerator.success(popularPostList, HttpStatus.OK);
  }

  @ApiOperation(value = "게시물 좋아요(추천 수)", notes = "게시물 좋아요를 누른다, 1인당 1개의 게시물에 1번만 좋아요를 누를 수 있다")
  @PostMapping("/post/{postId}/like")
  public ApiResponse<ApiResponse.CustomBody<SetPostLikeResponseDTO>> setPostLike(
      @PathVariable("postId") Long postId) {
    Long memberId = 1L;

    SetPostLikeDTO setPostLikeDTO = new SetPostLikeDTO(postId, memberId);
    SetPostLikeResponseDTO setPostLikeResponseDTO = postService.setPostLike(setPostLikeDTO);

    return ApiResponseGenerator.success(setPostLikeResponseDTO, HttpStatus.OK);
  }

}
