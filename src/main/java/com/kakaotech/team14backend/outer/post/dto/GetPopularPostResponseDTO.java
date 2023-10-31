package com.kakaotech.team14backend.outer.post.dto;

import java.util.List;

public record GetPopularPostResponseDTO(Long postId, String imageUri, List<String> hashTags, Long likeCount, Integer postPoint, String nickname,Boolean isLiked) {
}
