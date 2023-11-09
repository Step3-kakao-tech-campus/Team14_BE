package com.kakaotech.team14backend.post.dto;

import java.util.List;

public record GetPopularPostListResponseDTO(List<GetPopularPostDTO> popularPosts) {
}
