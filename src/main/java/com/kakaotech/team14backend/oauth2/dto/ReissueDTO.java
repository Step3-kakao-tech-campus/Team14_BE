package com.kakaotech.team14backend.oauth2.dto;

import lombok.Getter;

@Getter
public class ReissueDTO {
  String accessToken;

  public ReissueDTO(String accessToken) {
    this.accessToken = accessToken;
  }
}
