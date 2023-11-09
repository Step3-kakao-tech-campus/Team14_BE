package com.kakaotech.team14backend.post.dto;

public record SetNonAuthenticatedHomePostDTO(Long postId, String imageUri, String hashTags,

                                             String nickname) implements
    GetHomePostInterface {
}
