package com.kakaotech.team14backend.outer.post.dto;

import com.kakaotech.team14backend.inner.member.model.Member;

public record UploadPostDTO(Member member, UploadPostRequestDTO uploadPostRequestDTO) {
}
