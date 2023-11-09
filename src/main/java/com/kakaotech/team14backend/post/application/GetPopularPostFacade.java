package com.kakaotech.team14backend.post.application;

import com.kakaotech.team14backend.inner.post.usecase.FindPopularPost;
import com.kakaotech.team14backend.outer.post.dto.GetPopularPostListRequestDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPopularPostListResponseDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPopularPostResponseDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPostDTO;
import com.kakaotech.team14backend.outer.post.dto.PostLevelPoint;
import com.kakaotech.team14backend.post.infrastructure.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetPopularPostFacade {

  private final FindPopularPosts findPopularPosts;
  private final SavePostViewCount savePostViewCount;
  private final GetPopularPostPoint getPopularPostPoint;
  private final FindPopularPost findPopularPost;
  private final PostRepository postRepository;

  public GetPopularPostListResponseDTO getPopularPostList(
      GetPopularPostListRequestDTO getPopularPostListRequestDTO) {
    int size = postRepository.findAll().size();
    return findPopularPosts.execute(getPopularPostListRequestDTO, size);
  }

  public GetPopularPostResponseDTO getPopularPost(GetPostDTO getPostDTO) {
    savePostViewCount.execute(getPostDTO);
    PostLevelPoint postLevelPoint = getPopularPostPoint.execute(getPostDTO.postId());
    return findPopularPost.execute(getPostDTO, postLevelPoint);
  }
}
