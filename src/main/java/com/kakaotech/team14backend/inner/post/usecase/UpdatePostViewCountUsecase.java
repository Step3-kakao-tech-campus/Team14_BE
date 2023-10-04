package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.inner.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UpdatePostViewCountUsecase {

  private final RedisTemplate<String,Object> redisTemplate;

  private final PostRepository postRepository;
  @Transactional
  public void execute() {
    Set<String> keys = redisTemplate.keys("viewCnt*");
    for(String key : keys){
      Object cnt = redisTemplate.opsForValue().get(key);
      Post post = postRepository.findById(splitKey(key)).orElseThrow(() -> new RuntimeException("Post not found"));
      post.updateViewCount(Long.valueOf((String) cnt));
    }
    // mysql에 update!
    clearPostViewCount();
  }

  @CacheEvict(value = "viewCnt", allEntries = true)
  public void clearPostViewCount(){

  }

  private Long splitKey(String key){
    List<String> stringList = Arrays.stream(key.split("::")).collect(Collectors.toList());
    return Long.valueOf(stringList.get(1));
  }

}
