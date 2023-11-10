package com.kakaotech.team14backend.post.dto;

public record SetAuthenticatedHomePostDTO(long postId, String imageUri, String hashTags,
                                          Integer postPoint,
                                          String nickname, boolean isLiked) {
}
