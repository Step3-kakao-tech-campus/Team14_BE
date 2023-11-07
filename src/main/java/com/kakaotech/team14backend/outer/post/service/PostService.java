package com.kakaotech.team14backend.outer.post.service;

import com.kakaotech.team14backend.inner.image.model.Image;
import com.kakaotech.team14backend.inner.image.usecase.CreateImageUsecase;
import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.point.model.GetPointPolicy;
import com.kakaotech.team14backend.inner.point.usecase.GetPointUsecase;
import com.kakaotech.team14backend.inner.post.port.PostUseCasePort;
import com.kakaotech.team14backend.inner.post.usecase.CreatePostUsecase;
import com.kakaotech.team14backend.inner.post.usecase.FindMyPostUsecase;
import com.kakaotech.team14backend.inner.post.usecase.FindNonAuthPostListUsecase;
import com.kakaotech.team14backend.inner.post.usecase.FindPersonalPostListUsecase;
import com.kakaotech.team14backend.inner.post.usecase.FindPopularPostListUsecase;
import com.kakaotech.team14backend.inner.post.usecase.FindPopularPostUsecase;
import com.kakaotech.team14backend.inner.post.usecase.FindPostListUsecase;
import com.kakaotech.team14backend.inner.post.usecase.FindPostUsecase;
import com.kakaotech.team14backend.inner.post.usecase.GetPopularPostPointUsecase;
import com.kakaotech.team14backend.inner.post.usecase.SaveTemporaryPostViewCountUsecase;
import com.kakaotech.team14backend.inner.post.usecase.SetPostLikeUsecase;
import com.kakaotech.team14backend.inner.post.usecase.UpdatePostLikeCountUsecase;
import com.kakaotech.team14backend.outer.post.dto.CreatePostDTO;
import com.kakaotech.team14backend.outer.post.dto.GetHomePostListResponseDTO;
import com.kakaotech.team14backend.outer.post.dto.GetMyPostResponseDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPersonalPostListResponseDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPopularPostListRequestDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPopularPostListResponseDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPopularPostResponseDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPostDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPostLikeCountDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPostResponseDTO;
import com.kakaotech.team14backend.outer.post.dto.PostLevelPoint;
import com.kakaotech.team14backend.outer.post.dto.SetPostLikeDTO;
import com.kakaotech.team14backend.outer.post.dto.SetPostLikeResponseDTO;
import com.kakaotech.team14backend.outer.post.dto.UploadPostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

  private final CreateImageUsecase createImageUsecase;
  private final CreatePostUsecase createPostUsecase;
  private final FindPostUsecase findPostUsecase;
  private final FindPostListUsecase findPostListUsecase;
  private final FindPopularPostUsecase findPopularPostUsecase;
  private final SaveTemporaryPostViewCountUsecase saveTemporaryPostViewCountUsecase;
  private final SetPostLikeUsecase setPostLikeUsecase;
  private final FindPopularPostListUsecase findPopularPostListUsecase;
  private final UpdatePostLikeCountUsecase updatePostLikeCountUsecase;
  private final FindPersonalPostListUsecase findPersonalPostListUsecase;
  private final FindMyPostUsecase findMyPostUsecase;
  private final FindNonAuthPostListUsecase findNonAuthPostListUsecase;
  private final PostUseCasePort postUseCasePort;
  private final GetPopularPostPointUsecase getPopularPostPointUsecase;

  private final GetPointUsecase getPointUsecase;

  public GetPersonalPostListResponseDTO getPersonalPostList(Long userId, Long lastPostId,
      int size) {

    return findPersonalPostListUsecase.execute(userId, lastPostId, size);
  }

  @Transactional
  public void uploadPost(UploadPostDTO uploadPostDTO){
    Member savedMember = uploadPostDTO.member();
    Image savedImage = createImageUsecase.execute(uploadPostDTO.uploadPostRequestDTO().getImage());
    CreatePostDTO createPostDTO = new CreatePostDTO(savedImage,
        uploadPostDTO.uploadPostRequestDTO(), savedMember);
    createPostUsecase.execute(createPostDTO);
    getPointUsecase.execute(savedMember, GetPointPolicy.GIVE_300_WHEN_UPLOAD);
  }

  public GetPostResponseDTO getPost(GetPostDTO getPostDTO) {
    saveTemporaryPostViewCountUsecase.execute(getPostDTO);
    return findPostUsecase.execute(getPostDTO);
  }

  public GetHomePostListResponseDTO getAuthenticatedPostList(Long lastPostId, int size,
      Long memberId) {
    return postUseCasePort.getAuthenticatedPostList(lastPostId, size, memberId);
  }

  public GetHomePostListResponseDTO getNonAuthenticatedPostList(Long lastPostId, int size) {
    return findNonAuthPostListUsecase.execute(lastPostId, size);
  }

  /**
   * 인기 게시물을 상세조회한다.
   *
   * @return : 인기 게시물 상세 조회시 반환 값
   * @author : hwangdaesun
   * @see : saveTemporaryPostViewCountUsecase는 게시물 조회시 게시물의 조회수를 늘려주는 클래스
   */

  public GetPopularPostResponseDTO getPopularPost(GetPostDTO getPostDTO) {
    saveTemporaryPostViewCountUsecase.execute(getPostDTO);
    PostLevelPoint postLevelPoint = getPopularPostPointUsecase.execute(getPostDTO.postId());
    return findPopularPostUsecase.execute(getPostDTO, postLevelPoint);
  }

  /*
   * 게시물 좋아요를 설정한다.
   * @param : 게시물 구분자, 유저 구분자
   * @return : 게시물 좋아요 설정시 반환 값
   * @see : setPostLikeUsecase는 게시물 좋아요를 설정하는 클래스
   */
  @Transactional
  public SetPostLikeResponseDTO setPostLike(SetPostLikeDTO setPostLikeDTO) {
    SetPostLikeResponseDTO isLiked = setPostLikeUsecase.execute(setPostLikeDTO);

    Long postId = setPostLikeDTO.postId();
    GetPostLikeCountDTO getPostLikeCountDTO = new GetPostLikeCountDTO(postId, isLiked.isLiked());

    // 좋아요 상태를 반환 할 때 카운트를 업데이트
    updatePostLikeCountUsecase.execute(getPostLikeCountDTO);
    return isLiked;
  }

  /**
   * 인기 게시물 전체 조회
   *
   * @return : 인기 게시물들 응답
   * @author : hwangdaesun
   */

  public GetPopularPostListResponseDTO getPopularPostList(
      GetPopularPostListRequestDTO getPopularPostListRequestDTO) {
    int size = findPostListUsecase.findPostListSize();
    return findPopularPostListUsecase.execute(
        getPopularPostListRequestDTO.levelSize(), size);
  }

  public GetMyPostResponseDTO getMyPost(Long memberId, Long postId) {
    return findMyPostUsecase.execute(memberId, postId);
  }
}
