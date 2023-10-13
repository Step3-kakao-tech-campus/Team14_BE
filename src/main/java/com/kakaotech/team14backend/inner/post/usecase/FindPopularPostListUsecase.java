package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.common.RedisKey;
import com.kakaotech.team14backend.inner.post.model.PostRandomFetcher;
import com.kakaotech.team14backend.outer.post.dto.GetIncompletePopularPostDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPopularPostListResponseDTO;
import com.kakaotech.team14backend.outer.post.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
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

    Map<Integer, Set<GetIncompletePopularPostDTO>> levelPosts = new HashMap<>();

    for(int i = 1; i <= levelIndexes.size(); i++){

      Set<GetIncompletePopularPostDTO> incompletePopularPostDTOS = new HashSet<>();
      incompletePopularPostDTOS.clear();

      for(int j = 0; j < levelIndexes.get(i).size(); j++){

        Set<LinkedHashMap<String, Object>> posts = redisTemplate.opsForZSet().range(RedisKey.POPULAR_POST_KEY.getKey(), levelIndexes.get(i).get(j)-1, levelIndexes.get(i).get(j)-1);
        Long size = redisTemplate.opsForZSet().size(RedisKey.POPULAR_POST_KEY.getKey());

        List<GetIncompletePopularPostDTO> getIncompletePopularPostDTOs = getIncompletePopularPostDTOs(posts);

        incompletePopularPostDTOS.add(getIncompletePopularPostDTOs.get(0));
      }
      levelPosts.put(i,incompletePopularPostDTOS);
    }

    GetPopularPostListResponseDTO getPopularPostListResponseDTO = PostMapper.from(levelPosts);
    return getPopularPostListResponseDTO;
  }

  private List<GetIncompletePopularPostDTO> getIncompletePopularPostDTOs(Set<LinkedHashMap<String, Object>> posts) {
    return posts.stream().map(postMap -> {

      Long postId = castToLong((Integer) postMap.get("postId"));
      String imageUri = (String) postMap.get("imageUri");
      String hashTag = (String) postMap.get("hashTag");
      Long likeCount = castToLong((Integer) postMap.get("likeCount"));
      Integer postPoint = (Integer) postMap.get("postPoint");
      Long popularity = castToLong((Integer) postMap.get("popularity"));
      String nickname = (String) postMap.get("nickname");

      return new GetIncompletePopularPostDTO(
          postId, imageUri, hashTag, likeCount, postPoint, popularity, nickname
      );
    }).collect(Collectors.toList());
  }

  private Long castToLong(Integer have){
    Long want = have.longValue();
    return want;
  }
}
