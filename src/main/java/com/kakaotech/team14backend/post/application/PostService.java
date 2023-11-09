package com.kakaotech.team14backend.post.application;

import com.kakaotech.team14backend.inner.post.usecase.FindMyPostUsecase;
import com.kakaotech.team14backend.inner.post.usecase.FindNonAuthPostListUsecase;
import com.kakaotech.team14backend.inner.post.usecase.FindPersonalPostListUsecase;
import com.kakaotech.team14backend.inner.post.usecase.FindPostListUsecase;
import com.kakaotech.team14backend.inner.post.usecase.FindPostUsecase;
import com.kakaotech.team14backend.inner.post.usecase.SetPostLikeUsecase;
import com.kakaotech.team14backend.inner.post.usecase.UpdatePostLikeCountUsecase;
import com.kakaotech.team14backend.post.dto.GetHomePostListResponseDTO;
import com.kakaotech.team14backend.post.dto.GetMyPostResponseDTO;
import com.kakaotech.team14backend.post.dto.GetPersonalPostListResponseDTO;
import com.kakaotech.team14backend.post.dto.GetPostDTO;
import com.kakaotech.team14backend.post.dto.GetPostLikeCountDTO;
import com.kakaotech.team14backend.post.dto.GetPostResponseDTO;
import com.kakaotech.team14backend.post.dto.SetPostLikeDTO;
import com.kakaotech.team14backend.post.dto.SetPostLikeResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

  private final FindPostUsecase findPostUsecase;
  private final SavePostViewCount savePostViewCount;
  private final SetPostLikeUsecase setPostLikeUsecase;
  private final UpdatePostLikeCountUsecase updatePostLikeCountUsecase;
  private final FindPersonalPostListUsecase findPersonalPostListUsecase;
  private final FindMyPostUsecase findMyPostUsecase;
  private final FindNonAuthPostListUsecase findNonAuthPostListUsecase;
  private final FindPostListUsecase findPostListUsecase;


  public GetPersonalPostListResponseDTO getPersonalPostList(Long userId, Long lastPostId,
      int size) {

    return findPersonalPostListUsecase.execute(userId, lastPostId, size);
  }

  public GetPostResponseDTO getPost(GetPostDTO getPostDTO) {
    savePostViewCount.execute(getPostDTO);
    return findPostUsecase.execute(getPostDTO);
  }


  public GetHomePostListResponseDTO getHomePostList(Long lastPostId, int size,
      Long memberId) {
    if (memberId == null) {
      return findNonAuthPostListUsecase.execute(lastPostId, size);
    }
    return findPostListUsecase.execute(lastPostId, size, memberId);
  }

  /**
   * 인기 게시물을 상세조회한다.
   *
   * @return : 인기 게시물 상세 조회시 반환 값
   * @author : hwangdaesun
   * @see : saveTemporaryPostViewCountUsecase는 게시물 조회시 게시물의 조회수를 늘려주는 클래스
   */

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

  public GetMyPostResponseDTO getMyPost(Long memberId, Long postId) {
    GetPostDTO getPostDTO = new GetPostDTO(postId, memberId);
    savePostViewCount.execute(getPostDTO);
    return findMyPostUsecase.execute(memberId, postId);
  }
}
