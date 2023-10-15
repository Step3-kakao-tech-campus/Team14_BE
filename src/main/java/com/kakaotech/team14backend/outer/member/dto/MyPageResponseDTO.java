package com.kakaotech.team14backend.outer.member.dto;

import lombok.Builder;

public class MyPageResponseDTO {

  private Long memberId;
  private String userName;
  private String kakaoId;
  private Long totalLike;

  @Builder
  public MyPageResponseDTO(Long memberId, String userName, String kakaoId, Long totalLike) {
    this.memberId = memberId;
    this.userName = userName;
    this.kakaoId = kakaoId;
    this.totalLike = totalLike;
  }
}


