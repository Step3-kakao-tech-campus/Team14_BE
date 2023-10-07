package com.kakaotech.team14backend.outer.post.service;

import com.kakaotech.team14backend.inner.image.model.Image;
import com.kakaotech.team14backend.inner.image.usecase.CreateImageUsecase;
import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.member.usecase.FindMemberUsecase;
import com.kakaotech.team14backend.inner.post.usecase.CreatePostUsecase;
import com.kakaotech.team14backend.inner.post.usecase.FindPopularPostListUsecase;
import com.kakaotech.team14backend.inner.post.usecase.FindPopularPostUsecase;
import com.kakaotech.team14backend.inner.post.usecase.FindPostListUsecase;
import com.kakaotech.team14backend.inner.post.usecase.FindPostUsecase;
import com.kakaotech.team14backend.inner.post.usecase.UpdatePostLikeCountUsecase;
import com.kakaotech.team14backend.inner.post.usecase.SetPostLikeUsecase;
import com.kakaotech.team14backend.inner.post.usecase.SaveTemporaryPostViewCountUsecase;
import com.kakaotech.team14backend.outer.post.dto.CreatePostDTO;
import com.kakaotech.team14backend.outer.post.dto.GetIncompletePopularPostDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPopularPostListRequestDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPopularPostListResponseDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPostDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPostLikeCountDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPostListResponseDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPostResponseDTO;
import com.kakaotech.team14backend.outer.post.dto.SetPostLikeDTO;
import com.kakaotech.team14backend.outer.post.dto.SetPostLikeResponseDTO;
import com.kakaotech.team14backend.outer.post.dto.UploadPostDTO;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

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
  private final SaveTemporaryPostViewCountUsecase saveTemporaryPostViewCountUsecase;
  private final SetPostLikeUsecase setPostLikeUsecase;
<<<<<<< HEAD
  private final FindPopularPostListUsecase findPopularPostListUsecase;
=======
  private final UpdatePostLikeCountUsecase updatePostLikeCountUsecase;
>>>>>>> f2bd7542ec45e03c38dcf36443a91228e1fb34d7

  @Transactional
  public void uploadPost(UploadPostDTO uploadPostDTO) throws IOException {
    Member savedMember = findMemberUsecase.execute(uploadPostDTO.memberId());
    Image savedImage = createImageUsecase.execute(uploadPostDTO.image(), uploadPostDTO.uploadPostRequestDTO().getImageName());
    CreatePostDTO createPostDTO = new CreatePostDTO(savedImage, uploadPostDTO.uploadPostRequestDTO(), savedMember);
    createPostUsecase.execute(createPostDTO);
  }

  public GetPostResponseDTO getPost(GetPostDTO getPostDTO) {
    saveTemporaryPostViewCountUsecase.execute(getPostDTO);
    GetPostResponseDTO getPostResponseDTO = findPostUsecase.execute(getPostDTO);
    return getPostResponseDTO;
  }

  public GetPostListResponseDTO getPostList(Long lastPostId, int size) {
    return findPostListUsecase.excute(lastPostId, size);
  }

  public GetPostResponseDTO getPopularPost(GetPostDTO getPostDTO) {
    saveTemporaryPostViewCountUsecase.execute(getPostDTO);
    GetPostResponseDTO getPostResponseDTO =findPopularPostUsecase.execute(getPostDTO);
    return getPostResponseDTO;
  }

  public SetPostLikeResponseDTO setPostLike(SetPostLikeDTO setPostLikeDTO) {
<<<<<<< HEAD
    return setPostLikeUsecase.execute(setPostLikeDTO);
=======
    SetPostLikeResponseDTO isLiked = setPostLikeUsecase.execute(setPostLikeDTO);

    Long postId = setPostLikeDTO.postId();
    GetPostLikeCountDTO getPostLikeCountDTO = new GetPostLikeCountDTO(postId, isLiked.isLiked());

    // 좋아요 상태를 반환 할 때 카운트를 업데이트
    updatePostLikeCountUsecase.execute(getPostLikeCountDTO);
    return isLiked;
>>>>>>> f2bd7542ec45e03c38dcf36443a91228e1fb34d7
  }

  public GetPopularPostListResponseDTO getPopularPostList(GetPopularPostListRequestDTO getPopularPostListRequestDTO){
    GetPopularPostListResponseDTO getPopularPostListResponseDTO = findPopularPostListUsecase.execute(getPopularPostListRequestDTO.levelSize());
    return getPopularPostListResponseDTO;
  }

}
