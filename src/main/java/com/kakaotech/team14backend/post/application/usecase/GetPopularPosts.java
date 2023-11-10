package com.kakaotech.team14backend.post.application.usecase;

import com.kakaotech.team14backend.post.application.command.FindPopularPosts;
import com.kakaotech.team14backend.post.dto.GetPopularPostListRequestDTO;
import com.kakaotech.team14backend.post.dto.GetPopularPostListResponseDTO;
import com.kakaotech.team14backend.post.infrastructure.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetPopularPosts {

  private final FindPopularPosts findPopularPosts;
  private final PostRepository postRepository;

  /**
   * 인기 게시물 전체 조회
   *
   * @return : 인기 게시물들 응답
   * @author : hwangdaesun
   */
  public GetPopularPostListResponseDTO execute(GetPopularPostListRequestDTO getPopularPostListRequestDTO) {
    int size = postRepository.findAll().size();
    return findPopularPosts.execute(getPopularPostListRequestDTO, size);
  }

}
