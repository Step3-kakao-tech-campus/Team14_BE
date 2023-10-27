package com.kakaotech.team14backend.outer.member.dto;

import lombok.Builder;

public record GetMemberInfoResponseDTO(Long memberId, String userName, String kakaoId,
                                       Long totalLike,
                                       String profileImageUrl) {


}


