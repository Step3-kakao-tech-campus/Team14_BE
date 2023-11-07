package com.kakaotech.team14backend.inner.image.usecase;

import com.kakaotech.team14backend.common.FileUtils;
import com.kakaotech.team14backend.common.UploadFileDTO;
import com.kakaotech.team14backend.inner.image.model.Image;
import com.kakaotech.team14backend.inner.image.repository.ImageRepository;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class CreateImageUsecase {

  //우리가 알던 서비스
  private final FileUtils fileUtils;
  private final ImageRepository imageRepository;

  public Image execute(MultipartFile image) throws IOException {
    UploadFileDTO uploadFileDTO = fileUtils.storeFile(image);
    Image createdImage = Image.createImage(fileUtils.getPath(uploadFileDTO.getStoreFileName()));
    return imageRepository.save(createdImage);
  }

}
