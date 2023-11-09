package com.kakaotech.team14backend.post.dto;

import com.kakaotech.team14backend.image.domain.Image;
import com.kakaotech.team14backend.inner.member.model.Member;

public record CreatePostDTO(Image image, UploadPostRequestDTO uploadPostRequestDTO, Member member) {
}
