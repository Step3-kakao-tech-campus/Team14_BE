package com.kakaotech.team14backend.outer.post.dto;

import java.util.List;

public record GetPopularPostDTO(Long postId, String imageUrl, List<String> hashTags, Long likeCount, String nickname, Integer postLevel) {
}
