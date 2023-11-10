package com.kakaotech.team14backend.post.dto;

import com.kakaotech.team14backend.member.domain.Member;

public record UploadPostDTO(Member member, UploadPostRequestDTO uploadPostRequestDTO) {
}
