package com.kakaotech.team14backend.common;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadFileDTO {

  private String originalFileName;
  private String storeFileName;

  //  private String fileSize; //필요하면 다시 가져오기

}
