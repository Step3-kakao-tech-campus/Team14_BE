package com.kakaotech.team14backend.outer.post.dto;

import java.util.List;

public record GetPopularPostResponseDTO(Long postId, String imageUrl, List<String> hashTags, Long likeCount, Long postPoint, String nickname,Boolean isLiked, Integer postLevel) {
}
