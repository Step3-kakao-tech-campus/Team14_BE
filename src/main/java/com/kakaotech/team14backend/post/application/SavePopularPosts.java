package com.kakaotech.team14backend.post.application;

import com.kakaotech.team14backend.common.RedisKey;
import com.kakaotech.team14backend.post.dto.GetIncompletePopularPostDTO;
import com.kakaotech.team14backend.post.infrastructure.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SavePopularPosts {

  private final PostRepository postRepository;
  private final RedisTemplate redisTemplate;
  private final int POPULARITY_SIZE = 300;

  /**
   * MySQL에서 인기도가 높은 상위 300개의 게시물을 가져와 이를 Redis에 자장한다.
   *
   * @author : hwangdaesun
   */

  @Transactional
  public void execute(){
    List<GetIncompletePopularPostDTO> top300Posts = getTop300Posts();
    deletePopularPostsCache();
    setPopularPostsCache(top300Posts);
  }

  private List<GetIncompletePopularPostDTO> getTop300Posts(){
    return postRepository.findTop300ByOrderByPopularityDesc(PageRequest.of(0, POPULARITY_SIZE));
  }

  private void deletePopularPostsCache(){
    redisTemplate.delete(RedisKey.POPULAR_POST_KEY.getKey());
  }

  private void setPopularPostsCache(List<GetIncompletePopularPostDTO> top300Posts){
    top300Posts.forEach(this::setPopularPostCache);
  }

  private void setPopularPostCache(GetIncompletePopularPostDTO dto) {
    ZSetOperations zSetOperations = redisTemplate.opsForZSet();
    double score = dto.getPopularity().doubleValue();
    zSetOperations.add(RedisKey.POPULAR_POST_KEY.getKey(), dto.getPostId(), score);
  }

}
