package com.kakaotech.team14backend.outer.post.dto;

import java.util.List;

public record GetPopularPostDTO(Long postId, String imageUri, List<String> hashTags, Long likeCount, Integer postPoint, String nickname, Integer postLevel) {
}
