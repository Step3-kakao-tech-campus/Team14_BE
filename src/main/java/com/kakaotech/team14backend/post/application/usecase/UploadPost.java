package com.kakaotech.team14backend.post.application.usecase;

import com.kakaotech.team14backend.image.application.CreateImage;
import com.kakaotech.team14backend.image.domain.Image;
import com.kakaotech.team14backend.point.application.command.GetPoint;
import com.kakaotech.team14backend.point.domain.GetPointPolicy;
import com.kakaotech.team14backend.post.application.command.SavePost;
import com.kakaotech.team14backend.post.dto.CreatePostDTO;
import com.kakaotech.team14backend.post.dto.UploadPostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UploadPost {

  private final CreateImage createImage;
  private final SavePost savePost;
  private final GetPoint getPoint;

  @Transactional
  public void execute(UploadPostDTO uploadPostDTO) {
    Image savedImage = createImage.execute(uploadPostDTO.uploadPostRequestDTO().getImage());
    savePost.execute(makeCreatePostDTO(uploadPostDTO, savedImage));
    getPoint.execute(uploadPostDTO.member(), GetPointPolicy.GIVE_300_WHEN_UPLOAD);
  }

  private CreatePostDTO makeCreatePostDTO(UploadPostDTO uploadPostDTO, Image savedImage) {
    return new CreatePostDTO(savedImage, uploadPostDTO.uploadPostRequestDTO(),
        uploadPostDTO.member());
  }
}
