package com.kakaotech.team14backend.outer.post.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetIncompletePopularPostDTO {

  private Long postId;
  private String imageUri;
  private String hashTag;
  private Long likeCount;
  private Integer postPoint;
  private Long popularity;
  private String nickname;

  public GetIncompletePopularPostDTO(Long postId, String imageUri, String hashTag, Long likeCount, Integer postPoint, Long popularity, String nickname) {
    this.postId = postId;
    this.imageUri = imageUri;
    this.hashTag = hashTag;
    this.likeCount = likeCount;
    this.postPoint = postPoint;
    this.popularity = popularity;
    this.nickname = nickname;
  }
}
