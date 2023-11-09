package com.kakaotech.team14backend.inner.post.model;
import com.kakaotech.team14backend.post.domain.PostRandomFetcher;
import com.kakaotech.team14backend.post.domain.RandomIndexes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

class PostRandomFetcherTest {

  @Test
  @DisplayName("limitSize가 30일경우")
  void fetchRandomIndexesForAllLevels() {
    Map<Integer, Integer> levelSize = new LinkedHashMap<>();
    levelSize.put(3, 4);
    levelSize.put(2, 3);
    levelSize.put(1, 3);

    int limitSize = 31;

    PostRandomFetcher postRandomFetcher = createPostRandomFetcher(levelSize, limitSize);
    Map<Integer, RandomIndexes> integerListMap = postRandomFetcher.getLevelIndexes().levelIndexes();

    for(Map.Entry<Integer, RandomIndexes> entry : integerListMap.entrySet()){
      System.out.println("key : " + entry.getKey() + " value : " + entry.getValue().getIndexes());
    }

    Assertions.assertEquals(4, integerListMap.get(3).getIndexes().size());
    Assertions.assertEquals(3, integerListMap.get(2).getIndexes().size());
    Assertions.assertEquals(3, integerListMap.get(1).getIndexes().size());
  }

  @Test
  @DisplayName("limitSize가 30이상일 경우")
  void fetchRandomIndexesForAllLevels_over_30() {
    Map<Integer, Integer> levelSize = new LinkedHashMap<>();
    levelSize.put(3, 4);
    levelSize.put(2, 3);
    levelSize.put(1, 3);

    int limitSize = 31;

    PostRandomFetcher postRandomFetcher = createPostRandomFetcher(levelSize, limitSize);
    Map<Integer, RandomIndexes> integerListMap = postRandomFetcher.getLevelIndexes().levelIndexes();

    for(Map.Entry<Integer, RandomIndexes> entry : integerListMap.entrySet()){
      System.out.println("key : " + entry.getKey() + " value : " + entry.getValue().getIndexes());
    }

    Assertions.assertEquals(4, integerListMap.get(3).getIndexes().size());
    Assertions.assertEquals(3, integerListMap.get(2).getIndexes().size());
    Assertions.assertEquals(3, integerListMap.get(1).getIndexes().size());
  }

  @Test
  @DisplayName("limitSize가 30보다 크고, 요구하는 총 개수가 최대치일 때")
  void fetchRandomIndexesForAllLevels_max_size() {
    Map<Integer, Integer> levelSize = new LinkedHashMap<>();

    levelSize.put(3, 9);
    levelSize.put(2, 9);
    levelSize.put(1, 9);

    int limitSize = 31;

    PostRandomFetcher postRandomFetcher = createPostRandomFetcher(levelSize, limitSize);
    Map<Integer, RandomIndexes> integerListMap = postRandomFetcher.getLevelIndexes().levelIndexes();

    for(Map.Entry<Integer, RandomIndexes> entry : integerListMap.entrySet()){
      System.out.println("key : " + entry.getKey() + " value : " + entry.getValue());
    }

    Assertions.assertEquals(9, integerListMap.get(1).getIndexes().size());
    Assertions.assertEquals(9, integerListMap.get(2).getIndexes().size());
    Assertions.assertEquals(9, integerListMap.get(3).getIndexes().size());
  }

  @Test
  @DisplayName("limitSize가 30보다 작고, 요구하는 총 개수의 합보다 클 경우")
  void fetchRandomIndexesUnder30ForAllLevels_20() {
    Map<Integer, Integer> levelSize = new LinkedHashMap<>();
    levelSize.put(3, 4);
    levelSize.put(2, 3);
    levelSize.put(1, 3);

    int totalSize = 0;
    int limitSize = 11;

    PostRandomFetcher postRandomFetcher = createPostRandomFetcher(levelSize, limitSize);
    Map<Integer, RandomIndexes> integerListMap = postRandomFetcher.getLevelIndexes().levelIndexes();

    for(Map.Entry<Integer, RandomIndexes> entry : integerListMap.entrySet()){
      System.out.println("key : " + entry.getKey() + " value : " + entry.getValue().getIndexes());
      totalSize += entry.getValue().getIndexes().size();
    }

    Assertions.assertEquals(5, totalSize);
    Assertions.assertEquals(4, integerListMap.get(3).getIndexes().size());

  }

  @Test
  @DisplayName("limitSize가 30보다 작고, 요구하는 총 개수의 합보다 작을 경우")
  void fetchRandomIndexesUnder30ForAllLevels_8() {
    Map<Integer, Integer> levelSize = new LinkedHashMap<>();
    levelSize.put(3, 4);
    levelSize.put(2, 3);
    levelSize.put(1, 3);

    int limitSize = 9;

    int totalSize = 0;
    PostRandomFetcher postRandomFetcher = createPostRandomFetcher(levelSize, limitSize);
    Map<Integer, RandomIndexes> integerListMap = postRandomFetcher.getLevelIndexes().levelIndexes();
    for(Map.Entry<Integer, RandomIndexes> entry : integerListMap.entrySet()){
      System.out.println("key : " + entry.getKey() + " value : " + entry.getValue().getIndexes());
      totalSize += entry.getValue().getIndexes().size();
    }

    Assertions.assertEquals(4, totalSize);
    Assertions.assertEquals(4, integerListMap.get(3).getIndexes().size());

  }

  @Test
  @DisplayName("limitSize가 30보다 작고, level2 즉 11 ~ 20 범위 안에 들어오고, 요구하는 총 개수의 합보다 작을 경우")
  void fetchRandomIndexesUnder30ForAllLevels_14() {
    Map<Integer, Integer> levelSize = new LinkedHashMap<>();
    levelSize.put(3, 4);
    levelSize.put(2, 3);
    levelSize.put(1, 3);

    int limitSize = 14;

    int totalSize = 0;
    PostRandomFetcher postRandomFetcher = createPostRandomFetcher(levelSize, limitSize);
    Map<Integer, RandomIndexes> integerListMap = postRandomFetcher.getLevelIndexes().levelIndexes();
    for(Map.Entry<Integer, RandomIndexes> entry : integerListMap.entrySet()){
      System.out.println("key : " + entry.getKey() + " value : " + entry.getValue().getIndexes());
      totalSize += entry.getValue().getIndexes().size();
    }

    Assertions.assertEquals(7, totalSize);
    Assertions.assertEquals(4, integerListMap.get(3).getIndexes().size());
    Assertions.assertEquals(3, integerListMap.get(2).getIndexes().size());

  }

  @Test
  @DisplayName("limitSize가 30보다 작고, level2 즉 11 ~ 20 범위 안에 들어오고, 요구하는 총 개수의 합보다 작을 경우 하지만 이경우는 나올 수 있는 level2 등수가 11,12밖에 없다.")
  void fetchRandomIndexesUnder30ForAllLevels_12() {
    Map<Integer, Integer> levelSize = new LinkedHashMap<>();
    levelSize.put(3, 4);
    levelSize.put(2, 3);
    levelSize.put(1, 3);

    int limitSize = 12;

    int totalSize = 0;
    PostRandomFetcher postRandomFetcher = createPostRandomFetcher(levelSize, limitSize);
    Map<Integer, RandomIndexes> integerListMap = postRandomFetcher.getLevelIndexes().levelIndexes();
    for(Map.Entry<Integer, RandomIndexes> entry : integerListMap.entrySet()){
      System.out.println("key : " + entry.getKey() + " value : " + entry.getValue().getIndexes());
      totalSize += entry.getValue().getIndexes().size();
    }

    Assertions.assertEquals(6, totalSize);
    Assertions.assertEquals(4, integerListMap.get(3).getIndexes().size());
    Assertions.assertEquals(2, integerListMap.get(2).getIndexes().size());

  }

  private PostRandomFetcher createPostRandomFetcher(Map<Integer, Integer> levelSize, int limitSize) {
    return new PostRandomFetcher(levelSize, limitSize);
  }

}
