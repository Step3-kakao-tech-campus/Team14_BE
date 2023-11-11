package com.kakaotech.team14backend.post.dto;

import java.util.List;

public record GetPostResponseDTO(Long postId, String imageUrl, List<String> hashTags, Long likeCount, Integer postPoint, String nickname) {
}
