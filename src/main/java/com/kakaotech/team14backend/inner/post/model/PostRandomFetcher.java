package com.kakaotech.team14backend.inner.post.model;

import com.kakaotech.team14backend.outer.post.dto.RandomIndexes;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PostRandomFetcher {

  private final int MAX_RANK_SIZE = 30;


  public Map<Integer, RandomIndexes> fetchRandomIndexesForAllLevels(Map<Integer, Integer> levelCounts, int limitSize) {
    Map<Integer, RandomIndexes> result = new HashMap<>();

    if(isOverMaxSize(limitSize)){
      for (Map.Entry<Integer, Integer> entry : levelCounts.entrySet()) {
        int level = entry.getKey();
        int count = entry.getValue();
        result.put(level, fetchRandomIndexesForLevel(level, count));
      }
    }else{
      for (Map.Entry<Integer, Integer> entry : levelCounts.entrySet()) {
        int level = entry.getKey();
        int count = entry.getValue();
        result.put(level, fetchRandomIndexesForLevel(level, count, limitSize));
      }
    }

    return result;
  }

  private RandomIndexes fetchRandomIndexesForLevel(int level, int count) {
    int start = 0;
    int end = 0;

    switch (level) {
      case 3:
        start = PostLevel.LV3.start();
        end = PostLevel.LV3.end();
        break;
      case 2:
        start = PostLevel.LV2.start();
        end = PostLevel.LV2.end();
        break;
      case 1:
        start = PostLevel.LV1.start();
        end = PostLevel.LV1.end();
        break;
      default:
        throw new IllegalArgumentException("Invalid level: " + level);
    }

    return new RandomIndexes(start, end, count);
  }

  private RandomIndexes fetchRandomIndexesForLevel(int level, int count, int limitSize) {

    int start = 0;
    int end = 0;

    switch (level) {
      case 3:
        if(PostLevel.LV3.end() < limitSize){
          start = PostLevel.LV3.start();
          end = PostLevel.LV3.end();
        }else if(PostLevel.LV3.start() <= limitSize && PostLevel.LV3.end() > limitSize){
        start = PostLevel.LV3.start();
        end = limitSize;
      }else{
        return new RandomIndexes();
      }
        break;
      case 2:
        if(PostLevel.LV2.end() < limitSize) {
          start = PostLevel.LV2.start();
          end = PostLevel.LV2.end();
        }else if(PostLevel.LV2.start() <= limitSize && PostLevel.LV2.end() > limitSize){
          start = PostLevel.LV2.start();
          end = limitSize;
        }else{
          return new RandomIndexes();
        }
        break;
      case 1:
        if(PostLevel.LV1.end() < limitSize) {
          start = PostLevel.LV1.start();
          end = PostLevel.LV1.end();
        }else if(PostLevel.LV1.start() <= limitSize && PostLevel.LV1.end() > limitSize){
          start = PostLevel.LV1.start();
          end = limitSize;
        }else{
          return new RandomIndexes();
        }
        break;
      default:
        throw new IllegalArgumentException("Invalid level: " + level);
    }
    return new RandomIndexes(start, end, count);
  }

  private boolean isOverMaxSize(int limitSize) {
    return limitSize > MAX_RANK_SIZE ? true : false;
  }

}
