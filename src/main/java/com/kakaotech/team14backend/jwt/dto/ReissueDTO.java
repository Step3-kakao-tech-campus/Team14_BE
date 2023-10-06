package com.kakaotech.team14backend.jwt.dto;

import lombok.Getter;

@Getter
public class ReissueDTO {
  String accessToken;

  public ReissueDTO(String accessToken) {
    this.accessToken = accessToken;
  }
}
