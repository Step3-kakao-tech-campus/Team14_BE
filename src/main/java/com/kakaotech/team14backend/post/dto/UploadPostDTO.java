package com.kakaotech.team14backend.post.dto;

import com.kakaotech.team14backend.inner.member.model.Member;

public record UploadPostDTO(Member member, UploadPostRequestDTO uploadPostRequestDTO) {
}
