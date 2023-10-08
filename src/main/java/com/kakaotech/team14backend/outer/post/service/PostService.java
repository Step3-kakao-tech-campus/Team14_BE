package com.kakaotech.team14backend.outer.post.service;

import com.kakaotech.team14backend.inner.image.model.Image;
import com.kakaotech.team14backend.inner.image.usecase.CreateImageUsecase;
import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.member.usecase.FindMemberUsecase;
import com.kakaotech.team14backend.outer.post.dto.UploadPostRequestDTO;
import com.kakaotech.team14backend.inner.post.usecase.CreatePostUsecase;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

  private final CreateImageUsecase createImageUsecase;
  private final CreatePostUsecase createPostUsecase;
  private final FindMemberUsecase findMemberUsecase;

  @Transactional
  public void uploadPost(Long userId, MultipartFile image,
      UploadPostRequestDTO uploadPostRequestDTO) throws IOException {
    Member savedMember = findMemberUsecase.execute(userId);
    Image savedImage = createImageUsecase.execute(image, uploadPostRequestDTO.getImageName());
    createPostUsecase.execute(savedImage, uploadPostRequestDTO, savedMember);
  }
}
