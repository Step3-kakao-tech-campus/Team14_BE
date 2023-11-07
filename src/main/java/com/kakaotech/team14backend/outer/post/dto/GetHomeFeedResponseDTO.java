package com.kakaotech.team14backend.outer.post.dto;

import java.util.List;

public class GetHomeFeedResponseDTO {
  private Long postId;
  private String imageUri;
  private List<String> hashTags;
  private String nickname;


  public GetHomeFeedResponseDTO(Long postId, String imageUri, List<String> hashTags,
                                String nickname) {
    this.postId = postId;
    this.imageUri = imageUri;
    this.hashTags = hashTags;
    this.nickname = nickname;
  }
}
