package com.kakaotech.team14backend.post.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class UploadPostRequestDTO {

  private List<String> hashTags;
  private String nickname;
  private MultipartFile image;

}
