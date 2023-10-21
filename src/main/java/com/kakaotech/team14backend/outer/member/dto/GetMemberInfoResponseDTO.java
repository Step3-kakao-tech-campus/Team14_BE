package com.kakaotech.team14backend.outer.member.dto;

import lombok.Builder;

public class GetMemberInfoResponseDTO {

  private Long memberId;
  private String userName;
  private String kakaoId;
  private Long totalLike;
  private String profileImageUrl;

  @Builder
  public GetMemberInfoResponseDTO(Long memberId, String userName, String kakaoId, Long totalLike, String profileImageUrl) {
    this.memberId = memberId;
    this.userName = userName;
    this.kakaoId = kakaoId;
    this.totalLike = totalLike;
    this.profileImageUrl = profileImageUrl;
  }
}


