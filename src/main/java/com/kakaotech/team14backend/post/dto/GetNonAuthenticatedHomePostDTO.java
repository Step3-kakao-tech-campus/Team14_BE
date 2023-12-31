package com.kakaotech.team14backend.post.dto;

import java.util.List;

public record GetNonAuthenticatedHomePostDTO(Long postId, String imageUri, List<String> hashTags,

                                             String nickname) implements
    GetHomePostInterface {
}
