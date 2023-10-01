package com.kakaotech.team14backend.outer.post.service;

import com.kakaotech.team14backend.inner.image.model.Image;
import com.kakaotech.team14backend.inner.image.usecase.CreateImageUsecase;
import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.member.usecase.FindMemberUsecase;
import com.kakaotech.team14backend.inner.post.usecase.FindPostListUsecase;
import com.kakaotech.team14backend.inner.post.usecase.FindPopularPostUsecase;
import com.kakaotech.team14backend.inner.post.usecase.FindPostUsecase;
import com.kakaotech.team14backend.inner.post.usecase.UpdatePostViewCountUsecase;
import com.kakaotech.team14backend.outer.post.dto.CreatePostDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPostDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPostListResponseDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPostResponseDTO;
import com.kakaotech.team14backend.outer.post.dto.UploadPostDTO;
import com.kakaotech.team14backend.inner.post.usecase.CreatePostUsecase;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

  private final CreateImageUsecase createImageUsecase;
  private final CreatePostUsecase createPostUsecase;
  private final FindMemberUsecase findMemberUsecase;
  private final FindPostUsecase findPostUsecase;
  private final FindPostListUsecase findPostListUsecase;
  private final FindPopularPostUsecase findPopularPostUsecase;
  private final UpdatePostViewCountUsecase updatePostViewCountUsecase;

  @Transactional
  public void uploadPost(UploadPostDTO uploadPostDTO) throws IOException {
    Member savedMember = findMemberUsecase.execute(uploadPostDTO.memberId());
    Image savedImage = createImageUsecase.execute(uploadPostDTO.image(), uploadPostDTO.uploadPostRequestDTO().getImageName());
    CreatePostDTO createPostDTO = new CreatePostDTO(savedImage, uploadPostDTO.uploadPostRequestDTO(), savedMember);
    createPostUsecase.execute(createPostDTO);
  }

  public GetPostResponseDTO getPost(GetPostDTO getPostDTO) {
    updatePostViewCountUsecase.execute(getPostDTO);
    GetPostResponseDTO getPostResponseDTO = findPostUsecase.execute(getPostDTO);
    return getPostResponseDTO;
  }

  public GetPostListResponseDTO getPostList(Long lastPostId, int size) {
    return findPostListUsecase.excute(lastPostId, size);
  }

  public GetPostResponseDTO getPopularPost(GetPostDTO getPostDTO) {
    updatePostViewCountUsecase.execute(getPostDTO);
    GetPostResponseDTO getPostResponseDTO =findPopularPostUsecase.execute(getPostDTO);
    return getPostResponseDTO;
  }

}
