package com.kakaotech.team14backend.outer.login.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KakaoProfileDTO {

  private String id;

  @JsonProperty("properties")
  private Properties properties;

  @Override
  public String toString() {
    return "KakaoProfileDTO{" +
        "id=" + id +
        ", nickname='" + properties.getNickname() + '\'' +
        ", profileImage='" + properties.getProfileImage() + '\'' +
        '}';
  }

  @Data
  public static class Properties {
    private String nickname;

    @JsonProperty("profile_image")
    private String profileImage;
  }
}

// toString, equals
