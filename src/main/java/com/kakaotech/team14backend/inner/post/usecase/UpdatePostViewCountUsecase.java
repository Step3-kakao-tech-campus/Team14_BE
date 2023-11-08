package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.common.MessageCode;
import com.kakaotech.team14backend.common.RedisKey;
import com.kakaotech.team14backend.common.ScanRedisKey;
import com.kakaotech.team14backend.exception.PostNotFoundException;
import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.inner.post.repository.PostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UpdatePostViewCountUsecase {

  private final RedisTemplate<String, Object> redisTemplate;

  private final PostRepository postRepository;

  /**
   * Redis에 저장된 게시물당 방문자 데이터를 이용해 MySQL에 조회수를 Update한 후 해당 key - value는 레디스에서 제거
   *
   * @author : hwangdaesun
   */

  @Transactional
  public void execute() {
    List<String> keys = getKeys();
    for (String key : keys) {
      Long cnt = redisTemplate.opsForSet().size(key);
      Post post = postRepository.findById(splitKey(key)).orElseThrow(() -> new PostNotFoundException());
      post.updateViewCount(cnt);
    }
    clearPostViewCount(keys);
  }

  private List<String> getKeys() {
    return ScanRedisKey.scanKeysWithPattern(RedisKey.VIEW_COUNT_PREFIX + "*",
        redisTemplate);
  }

  public void clearPostViewCount(List<String> keys) {
    for (String key : keys) {
      redisTemplate.delete(key);
    }
  }

  private Long splitKey(String key) {
    String[] split = key.split(":");
    return Long.valueOf(split[1]);
  }

}
