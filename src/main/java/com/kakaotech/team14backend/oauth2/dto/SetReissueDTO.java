package com.kakaotech.team14backend.oauth2.dto;

import lombok.Getter;

@Getter
public class SetReissueDTO {
  String accessToken;

  public SetReissueDTO(String accessToken) {
    this.accessToken = accessToken;
  }
}
