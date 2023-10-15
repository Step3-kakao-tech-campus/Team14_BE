package com.kakaotech.team14backend.outer.login.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class InstagramAccessTokenDto {
  public String uniqueKey;
  public String accessToken;
}
