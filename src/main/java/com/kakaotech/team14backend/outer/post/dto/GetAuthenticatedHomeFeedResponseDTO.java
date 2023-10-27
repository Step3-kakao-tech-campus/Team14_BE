package com.kakaotech.team14backend.outer.post.dto;

import java.util.List;

public class GetAuthenticatedHomeFeedResponseDTO extends GetHomeFeedResponseDTO {

  // 좋아요 눌렀는지 여부
  private boolean isLiked;
  // 방문 폭죽 값
  private Integer postPoint;

  public GetAuthenticatedHomeFeedResponseDTO(Long postId, String imageUri, List<String> hashTags,
                                             String nickname, boolean isLiked, Integer postPoint) {
    super(postId, imageUri, hashTags, nickname);
    this.isLiked = isLiked;
    this.postPoint = postPoint;

  }

}
