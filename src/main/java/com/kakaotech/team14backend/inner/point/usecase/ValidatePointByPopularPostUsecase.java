package com.kakaotech.team14backend.inner.point.usecase;

import com.kakaotech.team14backend.common.RedisKey;
import com.kakaotech.team14backend.exception.Exception400;
import com.kakaotech.team14backend.inner.post.model.PostLevel;
import com.kakaotech.team14backend.outer.point.dto.UsePointByPopularPostRequestDTO;
import com.kakaotech.team14backend.outer.post.dto.GetIncompletePopularPostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ValidatePointByPopularPostUsecase {

  private final RedisTemplate redisTemplate;

  public void execute(UsePointByPopularPostRequestDTO usePointByPopularPostRequestDTO) {
    int[] levelRange = PostLevel.getLevelRange(usePointByPopularPostRequestDTO.postLevel());
    Set<LinkedHashMap<String, Object>> posts = redisTemplate.opsForZSet().reverseRange(RedisKey.POPULAR_POST_KEY.getKey(), levelRange[0], levelRange[1]);
    List<GetIncompletePopularPostDTO> getIncompletePopularPostDTOs = getIncompletePopularPostDTOs(posts);

    boolean flag = false;

    for(int i = 0; i < getIncompletePopularPostDTOs.size(); i++){
      if(getIncompletePopularPostDTOs.get(i).getPostId().equals(usePointByPopularPostRequestDTO.postId())){
        flag = true;
        return;
      }
    }
    if(!flag){
      throw new Exception400("Invalid postId: " + usePointByPopularPostRequestDTO.postId());
    }

  }
  private List<GetIncompletePopularPostDTO> getIncompletePopularPostDTOs(Set<LinkedHashMap<String, Object>> posts) {
    return posts.stream().map(postMap -> {

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
  }

  private Long castToLong(Integer have){
    Long want = have.longValue();
    return want;
  }
}
