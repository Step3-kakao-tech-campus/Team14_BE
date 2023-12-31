package com.kakaotech.team14backend.image.application;

import com.kakaotech.team14backend.common.FileUtils;
import com.kakaotech.team14backend.common.UploadFileDTO;
import com.kakaotech.team14backend.image.domain.Image;
import com.kakaotech.team14backend.image.infrastructure.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class CreateImage {

  private final FileUtils fileUtils;
  private final ImageRepository imageRepository;

  public Image execute(MultipartFile image){
    UploadFileDTO uploadFileDTO = fileUtils.storeFile(image);
    Image createdImage = Image.createImage(getPath(uploadFileDTO));
    return imageRepository.save(createdImage);
  }

  private String getPath(UploadFileDTO uploadFileDTO) {
    return fileUtils.getPath(uploadFileDTO.getStoreFileName());
  }

}
