package com.kakaotech.team14backend.outer.post.dto;

import java.util.List;

public record GetMyPostResponseDTO(Long postId, String imageUrl, String nickname,
                                   List<String> hashTags,
                                   Long likeCount, boolean isLiked, long viewCount) {
}
