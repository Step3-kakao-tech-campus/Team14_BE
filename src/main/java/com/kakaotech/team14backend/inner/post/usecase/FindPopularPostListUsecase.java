package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.common.RedisKey;
import com.kakaotech.team14backend.inner.post.model.PostRandomFetcher;
import com.kakaotech.team14backend.outer.post.dto.GetIncompletePopularPostDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPopularPostDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPopularPostListResponseDTO;
import com.kakaotech.team14backend.outer.post.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FindPopularPostListUsecase {

  private final RedisTemplate redisTemplate;
  private final PostRandomFetcher postRandomFetcher;

  public GetPopularPostListResponseDTO execute(Map<Integer, Integer> levelCounts){

    Map<Integer, List<Integer>> levelIndexes = postRandomFetcher.fetchRandomIndexesForAllLevels(levelCounts);

    Map<Integer, Set<GetIncompletePopularPostDTO>> levelPosts = new HashMap<>();

    for(int i=0; i<levelIndexes.size(); i++){
      Set<GetIncompletePopularPostDTO> posts = redisTemplate.opsForZSet().reverseRange(RedisKey.POPULAR_POST.getKey(), levelIndexes.get(i + 1).get(0), levelIndexes.get(i + 1).get(1));
      levelPosts.put(i+1,posts);
    }

    GetPopularPostListResponseDTO getPopularPostListResponseDTO = PostMapper.from(levelPosts);
    return getPopularPostListResponseDTO;
  }
}
