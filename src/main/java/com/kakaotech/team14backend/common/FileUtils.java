package com.kakaotech.team14backend.common;

import com.kakaotech.team14backend.exception.ExtentionNotAllowedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class FileUtils {

  private final String rootPath = System.getProperty("user.dir");

  private final String fileDir = rootPath + "/src/main/resources/image/";

  private final List<String> fileExts = List.of("pdf", "jpg", "jpeg", "png");

  public String getFullPath(String filename) {
    return fileDir + filename;
  }

  public List<UploadFileDTO> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
    List<UploadFileDTO> storeFileResult = new ArrayList<>();
    for (MultipartFile multipartFile : multipartFiles) {
      if (!multipartFile.isEmpty()) {
        storeFileResult.add(storeFile(multipartFile));
      } else {
        throw new FileNotFoundException();
      }
    }
    return storeFileResult;
  }

  public UploadFileDTO storeFile(MultipartFile multipartFile)
      throws IOException, IllegalStateException {
    if (multipartFile.isEmpty()) {
      return null;
    }
    String originalFilename = multipartFile.getOriginalFilename();
    String storeFileName = createStoreFileName(originalFilename);
    multipartFile.transferTo(new File(getFullPath(storeFileName)));
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

  public MediaType toMediaType(String ext) {
    MediaType mediaType = MediaType.ALL;
    if (ext.equals("jpg") || ext.equals("jpeg")) {
      mediaType = MediaType.IMAGE_JPEG;
    } else if (ext.equals("png")) {
      mediaType = MediaType.IMAGE_PNG;
    } else if (ext.equals("pdf")) {
      mediaType = MediaType.APPLICATION_PDF;
    }
    return mediaType;
  }

}
