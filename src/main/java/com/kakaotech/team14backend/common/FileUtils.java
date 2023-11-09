package com.kakaotech.team14backend.common;

import com.kakaotech.team14backend.exception.ExtentionNotAllowedException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import com.kakaotech.team14backend.image.exception.ImageIOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Slf4j
public class FileUtils {

  private final String rootPath = System.getProperty("user.dir");

  private final String fullFileDir = rootPath + "/images/";

  private final List<String> fileExts = List.of("pdf", "jpg", "jpeg", "png");

  public String getFullPath(String filename) {
    return fullFileDir + filename;
  }

  public String getPath(String filename){
    return "/images/" + filename;
  }


  public UploadFileDTO storeFile(MultipartFile multipartFile) {
    if (multipartFile.isEmpty()) {
      return null;
    }
    File directory = new File(fullFileDir);
    if (!directory.exists()) {
      directory.mkdirs();
    }

    String originalFilename = multipartFile.getOriginalFilename();
    String storeFileName = createStoreFileName(originalFilename);
    try {
      multipartFile.transferTo(new File(getFullPath(storeFileName)));
    } catch (IOException e) {
      throw new ImageIOException();
    }
    return new UploadFileDTO(originalFilename, storeFileName);
  }


  // 파일명 뽑기 -
  private String createStoreFileName(String originalFilename) {
    String ext = extractExt(originalFilename);
    String uuid = UUID.randomUUID().toString();
    return uuid + "." + ext;
  }

  // 확장명 뽑기
  public String extractExt(String originalFilename) {
    int pos = originalFilename.lastIndexOf(".");
    String ext = originalFilename.substring(pos + 1);
    if (!fileExts.contains(ext)) {
      throw new ExtentionNotAllowedException(MessageCode.NOT_ALLOWED_FILE_EXT);
    }
    return ext;
  }


}
