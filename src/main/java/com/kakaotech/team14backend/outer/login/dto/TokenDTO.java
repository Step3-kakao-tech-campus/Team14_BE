package com.kakaotech.team14backend.outer.login.dto;

import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class TokenDTO {
  private String accessToken;
  private String refreshToken;

}
