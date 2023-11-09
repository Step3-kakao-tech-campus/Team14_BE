package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.post.exception.PostNotFoundException;
import com.kakaotech.team14backend.post.domain.Post;
import com.kakaotech.team14backend.inner.post.repository.PostLikeRepository;
import com.kakaotech.team14backend.post.infrastructure.PostRepository;
import com.kakaotech.team14backend.outer.post.dto.GetPopularPostResponseDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPostDTO;
import com.kakaotech.team14backend.outer.post.dto.PostLevelPoint;
import com.kakaotech.team14backend.post.mapper.PostMapper;
import com.kakaotech.team14backend.outer.post.service.FindLikeStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
public class FindPopularPost {

  private final PostRepository postRepository;
  private final PostLikeRepository postLikeRepository;
  private final FindLikeStatusService findLikeStatusService;
  /**
   * Redis에 해당 popularPost가 있다면 Redis에서 가져오고, 존재하지 않는다면 DB에서 가져온 후 Redis에 반영한다.
   *
   * @author : hwangdaesun
   * @return : 인기 게시물 상세 조회시 반환 값
   */

  @Transactional(readOnly = true)
  public GetPopularPostResponseDTO execute(GetPostDTO getPostDTO, PostLevelPoint postLevelPoint) {
    Post popularPost = postRepository.findById(getPostDTO.postId()).orElseThrow(() -> new PostNotFoundException());
    return PostMapper.from(popularPost, isLiked(getPostDTO, popularPost),postLevelPoint);
  }

  private Boolean isLiked(GetPostDTO getPostDTO, Post post){

    return findLikeStatusService.execute(getPostDTO.memberId(), post.getPostId());

  }

}
