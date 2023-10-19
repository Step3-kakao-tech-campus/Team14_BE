package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.common.RedisKey;
import com.kakaotech.team14backend.exception.Exception500;
import com.kakaotech.team14backend.inner.post.model.PostRandomFetcher;
import com.kakaotech.team14backend.outer.post.dto.GetIncompletePopularPostDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPopularPostListResponseDTO;
import com.kakaotech.team14backend.outer.post.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FindPopularPostListUsecase {

  private final RedisTemplate redisTemplate;
  private final PostRandomFetcher postRandomFetcher;

  public GetPopularPostListResponseDTO execute(Map<Integer, Integer> levelCounts){

    Map<Integer, List<Integer>> levelIndexes = postRandomFetcher.fetchRandomIndexesForAllLevels(levelCounts);

    List<GetIncompletePopularPostDTO> incompletePopularPostDTOS = new ArrayList<>();

    for(int i = 1; i <= levelIndexes.size(); i++){
      for(int j = 0; j < levelIndexes.get(i).size(); j++){
        Set<LinkedHashMap<String, Object>> post = redisTemplate.opsForZSet().range(RedisKey.POPULAR_POST_KEY.getKey(), levelIndexes.get(i).get(j)-1, levelIndexes.get(i).get(j)-1);
        incompletePopularPostDTOS.add(getIncompletePopularPostDTO(post));
      }
    }

    GetPopularPostListResponseDTO getPopularPostListResponseDTO = PostMapper.from(incompletePopularPostDTOS,levelIndexes);
    return getPopularPostListResponseDTO;
  }

  private GetIncompletePopularPostDTO getIncompletePopularPostDTO(Set<LinkedHashMap<String, Object>> post) {

    if(post.size() != 1){
      throw new Exception500("게시물이 1개만 조회되어야합니다.");
    }

    List<GetIncompletePopularPostDTO> popularPostDTOS = post.stream().map(postMap -> {

      Long postId = castToLong((Integer) postMap.get("postId"));
      String imageUri = (String) postMap.get("imageUri");
      String hashTag = (String) postMap.get("hashTag");
      Long likeCount = castToLong((Integer) postMap.get("likeCount"));
      Long popularity = castToLong((Integer) postMap.get("popularity"));
      String nickname = (String) postMap.get("nickname");

      return new GetIncompletePopularPostDTO(
          postId, imageUri, hashTag, likeCount, popularity, nickname
      );
    }).collect(Collectors.toList());

    return popularPostDTOS.get(0);
  }

  private Long castToLong(Integer have){
    Long want = have.longValue();
    return want;
  }
}
