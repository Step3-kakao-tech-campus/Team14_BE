package com.kakaotech.team14backend.member.dto;

public record GetMemberInfoResponseDTO(
    Long id,
    String username,
    String profileImage,
    Long fireworks,
    InstagramInfo instagram
) { }
