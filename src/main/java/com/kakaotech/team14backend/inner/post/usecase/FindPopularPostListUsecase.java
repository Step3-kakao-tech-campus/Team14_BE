package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.common.MessageCode;
import com.kakaotech.team14backend.common.RedisKey;
import com.kakaotech.team14backend.exception.MultiplePostsFoundException;
import com.kakaotech.team14backend.exception.PostNotFoundException;
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
  private static final int MINIMUM_SIZE = 30;
  public GetPopularPostListResponseDTO execute(Map<Integer, Integer> levelCounts, int size) {

    List<GetIncompletePopularPostDTO> incompletePopularPostDTOS = new ArrayList<>();
    GetPopularPostListResponseDTO getPopularPostListResponseDTO = null;
    Map<Integer, List<Integer>> levelIndexes = null;

    levelIndexes = getLevelIndexes(levelCounts, size);

    for (Map.Entry<Integer, List<Integer>> entry : levelIndexes.entrySet()) {
      List<Integer> indexes = entry.getValue();
      for (Integer index : indexes) {
        Set<LinkedHashMap<String, Object>> post = redisTemplate.opsForZSet().reverseRange(RedisKey.POPULAR_POST_KEY.getKey(), index - 1, index - 1);
        // todo 해당 게시물이 Redis에 없을 때 MySQL에서 조회하는 방법 생각!
        if (post.isEmpty()) {
          throw new PostNotFoundException(MessageCode.NOT_REGISTER_POST);
        }
        incompletePopularPostDTOS.add(getIncompletePopularPostDTO(post));
      }

    }

    getPopularPostListResponseDTO = PostMapper.from(incompletePopularPostDTOS, levelIndexes);

    return getPopularPostListResponseDTO;
  }

  private Map<Integer, List<Integer>> getLevelIndexes(Map<Integer, Integer> levelCounts, int size) {
    Map<Integer, List<Integer>> levelIndexes;
    if(size < MINIMUM_SIZE){
      levelIndexes = postRandomFetcher.fetchRandomIndexesUnder30ForAllLevels(levelCounts, size);

    }else{
      levelIndexes = postRandomFetcher.fetchRandomIndexesForAllLevels(levelCounts);

    }
    return levelIndexes;
  }

  private GetIncompletePopularPostDTO getIncompletePopularPostDTO(Set<LinkedHashMap<String, Object>> post) {

    if(post.size() != 1){
      throw new MultiplePostsFoundException(MessageCode.POST_MUST_FOUND_ONE);
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
