package com.kakaotech.team14backend.oauth2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class TokenDTO {
  private String accessToken;
  private String refreshToken;

  public TokenDTO(String accessToken, String refreshToken) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }
}
