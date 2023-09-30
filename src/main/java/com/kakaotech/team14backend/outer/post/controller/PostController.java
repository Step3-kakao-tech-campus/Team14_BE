package com.kakaotech.team14backend.outer.post.controller;

import com.kakaotech.team14backend.common.ApiResponse;
import com.kakaotech.team14backend.common.ApiResponseGenerator;
import com.kakaotech.team14backend.outer.post.dto.GetPostDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPostResponseDTO;
import com.kakaotech.team14backend.outer.post.dto.UploadPostDTO;
import com.kakaotech.team14backend.outer.post.dto.UploadPostRequestDTO;
import com.kakaotech.team14backend.outer.post.service.PostService;
import java.io.IOException;
import java.util.List;
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

  @GetMapping("/post")
  public ApiResponse<ApiResponse.CustomBody<List<GetPostResponseDTO>>> getPosts(
      @RequestParam(value = "lastPostId", required = false) Long lastItemId,
      @RequestParam(defaultValue = "10") int size) {

    List<GetPostResponseDTO> getPostListResponseDTO = postService.getPostList(lastItemId, size);
    return ApiResponseGenerator.success(getPostListResponseDTO, HttpStatus.CREATED);
  }


  @PostMapping("/post")
  public ApiResponse<ApiResponse.CustomBody<Void>> uploadPost(@RequestPart MultipartFile image,
      @RequestPart UploadPostRequestDTO uploadPostRequestDTO) throws IOException {

    Long memberId = 1L;
    UploadPostDTO uploadPostDTO = new UploadPostDTO(memberId, image, uploadPostRequestDTO);
    postService.uploadPost(uploadPostDTO);
    return ApiResponseGenerator.success(HttpStatus.CREATED);
  }


  @GetMapping("/post/{postId}")
  public ApiResponse<ApiResponse.CustomBody<GetPostResponseDTO>> getPost(@PathVariable("boadId") Long postId){
    Long memberId = 1L;
    GetPostDTO getPostDTO = new GetPostDTO(postId, memberId);
    GetPostResponseDTO getPostResponseDTO = postService.getPost(getPostDTO);
    return ApiResponseGenerator.success(getPostResponseDTO,HttpStatus.OK);
  }

}
