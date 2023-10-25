package com.kakaotech.team14backend.outer.post.dto;

import java.util.List;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadPostRequestDTO {

  private List<String> hashTags;
  private String nickname;
  private String imageName;
  private MultipartFile image;

}
