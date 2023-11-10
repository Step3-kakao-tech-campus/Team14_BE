package com.kakaotech.team14backend.post.application.usecase;

import com.kakaotech.team14backend.image.application.CreateImage;
import com.kakaotech.team14backend.image.domain.Image;
import com.kakaotech.team14backend.point.application.GetPointUsecase;
import com.kakaotech.team14backend.point.domain.GetPointPolicy;
import com.kakaotech.team14backend.post.dto.CreatePostDTO;
import com.kakaotech.team14backend.post.dto.UploadPostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UploadPost {

  private final CreateImage createImage;
  private final CreatePost createPostUsecase;
  private final GetPointUsecase getPointUsecase;

  @Transactional
  public void execute(UploadPostDTO uploadPostDTO) {
    Image savedImage = createImage.execute(uploadPostDTO.uploadPostRequestDTO().getImage());
    createPostUsecase.execute(makeCreatePostDTO(uploadPostDTO, savedImage));
    getPointUsecase.execute(uploadPostDTO.member(), GetPointPolicy.GIVE_300_WHEN_UPLOAD);
  }

  private CreatePostDTO makeCreatePostDTO(UploadPostDTO uploadPostDTO, Image savedImage) {
    return new CreatePostDTO(savedImage, uploadPostDTO.uploadPostRequestDTO(),
        uploadPostDTO.member());
  }
}
