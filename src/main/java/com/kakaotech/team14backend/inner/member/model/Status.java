package com.kakaotech.team14backend.inner.member.model;

public enum Status {
  STATUS_ACTIVE("STATUS_ACTIVE"),
  STATUS_DORMANT("STATUS_DORMANT");

  String status;
  Status(String status){
    this.status = status;
  }

  public String value(){
    return status;
  }
}
