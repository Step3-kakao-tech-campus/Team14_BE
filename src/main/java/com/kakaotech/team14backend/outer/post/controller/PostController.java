package com.kakaotech.team14backend.outer.post.controller;

import com.kakaotech.team14backend.common.ApiResponse;
import com.kakaotech.team14backend.common.ApiResponseGenerator;
import com.kakaotech.team14backend.outer.post.dto.UploadPostRequestDTO;
import com.kakaotech.team14backend.outer.post.service.PostService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class PostController {

  private final PostService postService;

  public ApiResponse<ApiResponse.CustomBody<Void>> uploadPost(@RequestPart MultipartFile image,
      @RequestPart UploadPostRequestDTO uploadPostRequestDTO) throws IOException {

    Long userId = 1L;
    postService.uploadPost(userId, image, uploadPostRequestDTO);
    return ApiResponseGenerator.success(HttpStatus.CREATED);
  }

}
