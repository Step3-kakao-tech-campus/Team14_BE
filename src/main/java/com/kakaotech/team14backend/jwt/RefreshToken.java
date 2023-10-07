package com.kakaotech.team14backend.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;


@AllArgsConstructor
@Getter
public class RefreshToken {

  @Id
  private String refreshToken;
  private String kakaoId;
}
