package com.kakaotech.team14backend.inner.member.model;

public enum Role {
  ROLE_BEGINNER("ROLE_BEGINNER"),
  ROLE_USER("ROLE_USER");

  String role;

  Role(String role){
    this.role = role;
  }

  public String value() {
    return role;
  }
}
