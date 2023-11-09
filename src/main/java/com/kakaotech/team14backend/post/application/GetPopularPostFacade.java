package com.kakaotech.team14backend.post.application;

import com.kakaotech.team14backend.inner.post.usecase.FindPopularPost;
import com.kakaotech.team14backend.inner.post.usecase.FindPostListUsecase;
import com.kakaotech.team14backend.post.dto.GetPopularPostListRequestDTO;
import com.kakaotech.team14backend.post.dto.GetPopularPostListResponseDTO;
import com.kakaotech.team14backend.post.dto.GetPopularPostResponseDTO;
import com.kakaotech.team14backend.post.dto.GetPostDTO;
import com.kakaotech.team14backend.post.dto.PostLevelPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetPopularPostFacade {

  private final FindPopularPosts findPopularPosts;
  private final FindPostListUsecase findPostListUsecase;
  private final SavePostViewCount savePostViewCount;
  private final GetPopularPostPoint getPopularPostPoint;
  private final FindPopularPost findPopularPost;

  public GetPopularPostListResponseDTO getPopularPostList(
      GetPopularPostListRequestDTO getPopularPostListRequestDTO) {
    int size = findPostListUsecase.findPostListSize();
    return findPopularPosts.execute(getPopularPostListRequestDTO, size);
  }

  public GetPopularPostResponseDTO getPopularPost(GetPostDTO getPostDTO) {
    savePostViewCount.execute(getPostDTO);
    PostLevelPoint postLevelPoint = getPopularPostPoint.execute(getPostDTO.postId());
    return findPopularPost.execute(getPostDTO, postLevelPoint);
  }
}
