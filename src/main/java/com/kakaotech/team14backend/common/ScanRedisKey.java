package com.kakaotech.team14backend.common;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ScanRedisKey {
  public static List<String> scanKeysWithPattern(String pattern, RedisTemplate<String, Object> redisTemplate) {

    List<String> keys = new ArrayList<>();

    ScanOptions options = ScanOptions.scanOptions()
        .match(pattern)
        .count(1000) // 한 번에 가져올 키의 최대 수 (옵션)
        .build();

    Cursor<byte[]> cursor = redisTemplate.executeWithStickyConnection(
        redisConnection -> redisConnection.scan(options)
    );

    while (cursor.hasNext()) {
      String key = new String(cursor.next());
      keys.add(key);
    }

    // 항상 cursor를 닫아주어야 합니다.
    cursor.close();
    return keys;
  }


}
