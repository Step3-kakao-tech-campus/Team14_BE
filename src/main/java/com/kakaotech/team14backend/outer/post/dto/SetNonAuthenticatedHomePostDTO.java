package com.kakaotech.team14backend.outer.post.dto;

public record SetNonAuthenticatedHomePostDTO(Long postId, String imageUri, String hashTags,

                                             String nickname) implements
    GetHomePostInterface {
}
