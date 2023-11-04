package com.kakaotech.team14backend.outer.member.dto;

public record GetLinkedMemberResponseDTO(
    Long id,
    String username,
    String profileImage,
    InstagramInfo instagram
) { }
