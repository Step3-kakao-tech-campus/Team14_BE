package com.kakaotech.team14backend.outer.post.service;

import com.kakaotech.team14backend.inner.image.model.Image;
import com.kakaotech.team14backend.inner.image.usecase.CreateImageUsecase;
import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.member.usecase.FindMemberUsecase;
import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.inner.post.usecase.FindPostListUsecase;
import com.kakaotech.team14backend.inner.post.usecase.FindPostUsecase;
import com.kakaotech.team14backend.outer.post.dto.CreatePostDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPostDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPostListResponseDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPostResponseDTO;
import com.kakaotech.team14backend.outer.post.dto.UploadPostDTO;
import com.kakaotech.team14backend.outer.post.dto.UploadPostRequestDTO;
import com.kakaotech.team14backend.inner.post.usecase.CreatePostUsecase;
import java.io.IOException;

import com.kakaotech.team14backend.outer.post.mapper.PostMapper;
import java.util.List;
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
  private final FindPostUsecase findPostUsecase;
  private final FindPostListUsecase findPostListUsecase;
  @Transactional
  public void uploadPost(UploadPostDTO uploadPostDTO) throws IOException {
    Member savedMember = findMemberUsecase.execute(uploadPostDTO.memberId());
    Image savedImage = createImageUsecase.execute(uploadPostDTO.image(), uploadPostDTO.uploadPostRequestDTO().getImageName());
    CreatePostDTO createPostDTO = new CreatePostDTO(savedImage, uploadPostDTO.uploadPostRequestDTO(), savedMember);
    createPostUsecase.execute(createPostDTO);
  }

  public GetPostResponseDTO getPost(GetPostDTO getPostDTO) {
    Post post = findPostUsecase.execute(getPostDTO);
    GetPostResponseDTO getPostResponseDTO = PostMapper.from(post);
    return getPostResponseDTO;
  }

  public GetPostListResponseDTO getPostList(Long lastPostId, int size) {

    return findPostListUsecase.excute(lastPostId, size);
  }
}
