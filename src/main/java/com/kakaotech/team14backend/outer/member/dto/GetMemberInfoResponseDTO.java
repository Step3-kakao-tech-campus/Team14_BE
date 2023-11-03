package com.kakaotech.team14backend.outer.member.dto;

public record GetMemberInfoResponseDTO(
    Long memberId,
    String userName,
    String kakaoId,
    Long totalLike,
    String profileImageUrl,
    boolean isInstaConnected,
    Long totalPoint
) {

}
