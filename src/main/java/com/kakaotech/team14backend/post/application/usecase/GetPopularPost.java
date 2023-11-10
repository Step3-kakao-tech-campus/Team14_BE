package com.kakaotech.team14backend.post.application.usecase;

import com.kakaotech.team14backend.post.application.command.FindPopularPost;
import com.kakaotech.team14backend.post.application.command.FindPopularPostPoint;
import com.kakaotech.team14backend.post.application.command.SavePostViewCount;
import com.kakaotech.team14backend.post.dto.GetPopularPostResponseDTO;
import com.kakaotech.team14backend.post.dto.GetPostDTO;
import com.kakaotech.team14backend.post.dto.PostLevelPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetPopularPost {

  private final SavePostViewCount savePostViewCount;
  private final FindPopularPostPoint findPopularPostPoint;
  private final FindPopularPost findPopularPost;

  /**
   * 인기 게시물을 상세조회한다.
   *
   * @return : 인기 게시물 상세 조회시 반환 값
   * @author : hwangdaesun
   * @see : saveTemporaryPostViewCountUsecase는 게시물 조회시 게시물의 조회수를 늘려주는 클래스
   */

  public GetPopularPostResponseDTO execute(GetPostDTO getPostDTO) {
    savePostViewCount.execute(getPostDTO);
    PostLevelPoint postLevelPoint = findPopularPostPoint.execute(getPostDTO.postId());
    return findPopularPost.execute(getPostDTO, postLevelPoint);
  }
}
