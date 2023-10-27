package com.kakaotech.team14backend.outer.post.dto;

import java.util.List;

public class GetNonAuthenticatedHomeFeedResponseDTO extends GetHomeFeedResponseDTO {


  public GetNonAuthenticatedHomeFeedResponseDTO(Long postId, String imageUri, List<String> hashTags,
                                                String nickname) {
    super(postId, imageUri, hashTags, nickname);
  }
}
