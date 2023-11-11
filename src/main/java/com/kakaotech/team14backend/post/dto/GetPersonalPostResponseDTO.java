package com.kakaotech.team14backend.post.dto;

public record GetPersonalPostResponseDTO(Long postId, String imageUri, String nickname,
                                         String createdAt, Long viewCount, Long likeCount) {

}

