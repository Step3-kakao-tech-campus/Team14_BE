package com.kakaotech.team14backend.member.domain;

public enum Role {
  ROLE_BEGINNER("BEGINNER"),
  ROLE_USER("USER");

  String role;

  Role(String role){
    this.role = role;
  }

  public String value() {
    return role;
  }
}
