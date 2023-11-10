package com.kakaotech.team14backend.member.domain;

public enum Status {
  STATUS_ACTIVE("STATUS_ACTIVE"),
  STATUS_INACTIVE("STATUS_INACTIVE");

  String status;
  Status(String status){
    this.status = status;
  }

  public String value(){
    return status;
  }
}
