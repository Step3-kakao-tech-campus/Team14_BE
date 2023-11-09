package com.kakaotech.team14backend.post.dto;

import java.util.List;

public record GetAuthenticatedHomePostDTO(Long postId, String imageUri, List<String> hashTags,
                                          Integer postPoint,
                                          String nickname, boolean isLiked) implements
    GetHomePostInterface {
}
