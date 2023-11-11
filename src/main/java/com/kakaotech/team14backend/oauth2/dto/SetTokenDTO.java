package com.kakaotech.team14backend.oauth2.dto;

import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class SetTokenDTO {
  private String accessToken;
  private String refreshToken;

  public SetTokenDTO(String accessToken, String refreshToken) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }
}
