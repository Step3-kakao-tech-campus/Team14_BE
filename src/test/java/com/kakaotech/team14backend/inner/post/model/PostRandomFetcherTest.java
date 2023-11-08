package com.kakaotech.team14backend.inner.post.model;

import com.kakaotech.team14backend.outer.post.dto.RandomIndexes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.LinkedHashMap;
import java.util.Map;

class PostRandomFetcherTest {

  private PostRandomFetcher postRandomFetcher;

  @BeforeEach
  void setUp() {
    postRandomFetcher = createPostRandomFetcher();
  }

  @Test
  @DisplayName("limitSize가 30일경우")
  void fetchRandomIndexesForAllLevels() {
    Map<Integer, Integer> levelSize = new LinkedHashMap<>();
    levelSize.put(3, 4);
    levelSize.put(2, 3);
    levelSize.put(1, 3);


    Map<Integer, RandomIndexes> integerRandomIndexesMap = postRandomFetcher.fetchRandomIndexesForAllLevels(levelSize, 30);
    for(Map.Entry<Integer, RandomIndexes> entry : integerRandomIndexesMap.entrySet()){
      System.out.println("key : " + entry.getKey() + " value : " + entry.getValue().getIndexes());
    }

    Assertions.assertEquals(4, integerRandomIndexesMap.get(3).getIndexes().size());
    Assertions.assertEquals(3, integerRandomIndexesMap.get(2).getIndexes().size());
    Assertions.assertEquals(3, integerRandomIndexesMap.get(1).getIndexes().size());
  }

  @Test
  @DisplayName("limitSize가 30이상일 경우")
  void fetchRandomIndexesForAllLevels_over_30() {
    Map<Integer, Integer> levelSize = new LinkedHashMap<>();
    levelSize.put(3, 4);
    levelSize.put(2, 3);
    levelSize.put(1, 3);


    Map<Integer, RandomIndexes> integerRandomIndexesMap = postRandomFetcher.fetchRandomIndexesForAllLevels(levelSize, 31);
    for(Map.Entry<Integer, RandomIndexes> entry : integerRandomIndexesMap.entrySet()){
      System.out.println("key : " + entry.getKey() + " value : " + entry.getValue().getIndexes());
    }

    Assertions.assertEquals(4, integerRandomIndexesMap.get(3).getIndexes().size());
    Assertions.assertEquals(3, integerRandomIndexesMap.get(2).getIndexes().size());
    Assertions.assertEquals(3, integerRandomIndexesMap.get(1).getIndexes().size());
  }

  @Test
  void fetchRandomIndexesForAllLevels_max_size() {
    Map<Integer, Integer> levelSize = new LinkedHashMap<>();

    levelSize.put(3, 9);
    levelSize.put(2, 9);
    levelSize.put(1, 9);

    Map<Integer, RandomIndexes> integerRandomIndexesMap = postRandomFetcher.fetchRandomIndexesForAllLevels(levelSize,31);
    for(Map.Entry<Integer, RandomIndexes> entry : integerRandomIndexesMap.entrySet()){
      System.out.println("key : " + entry.getKey() + " value : " + entry.getValue());
    }

    Assertions.assertEquals(9, integerRandomIndexesMap.get(1).getIndexes().size());
    Assertions.assertEquals(9, integerRandomIndexesMap.get(2).getIndexes().size());
    Assertions.assertEquals(9, integerRandomIndexesMap.get(3).getIndexes().size());
  }

  @Test
  @DisplayName("limitSize가 30보다 작고, 요구하는 총 개수의 합보다 클 경우")
  void fetchRandomIndexesUnder30ForAllLevels_20() {
    Map<Integer, Integer> levelSize = new LinkedHashMap<>();
    levelSize.put(3, 4);
    levelSize.put(2, 3);
    levelSize.put(1, 3);

    int totalSize = 0;

    Map<Integer, RandomIndexes> integerRandomIndexesMap = postRandomFetcher.fetchRandomIndexesForAllLevels(levelSize, 11);
    for(Map.Entry<Integer, RandomIndexes> entry : integerRandomIndexesMap.entrySet()){
      System.out.println("key : " + entry.getKey() + " value : " + entry.getValue().getIndexes());
      totalSize += entry.getValue().getIndexes().size();
    }

    Assertions.assertEquals(4, totalSize);
    Assertions.assertEquals(4, integerRandomIndexesMap.get(3).getIndexes().size());

  }

  @Test
  @DisplayName("limitSize가 30보다 작고, 요구하는 총 개수의 합보다 작을 경우")
  void fetchRandomIndexesUnder30ForAllLevels_8() {
    Map<Integer, Integer> levelSize = new LinkedHashMap<>();
    levelSize.put(3, 4);
    levelSize.put(2, 3);
    levelSize.put(1, 3);


    int totalSize = 0;
    Map<Integer, RandomIndexes> integerListMap = postRandomFetcher.fetchRandomIndexesForAllLevels(levelSize, 9);
    for(Map.Entry<Integer, RandomIndexes> entry : integerListMap.entrySet()){
      System.out.println("key : " + entry.getKey() + " value : " + entry.getValue());
      totalSize += entry.getValue().getIndexes().size();
    }

    Assertions.assertEquals(4, totalSize);
    Assertions.assertEquals(4, integerListMap.get(3).getIndexes().size());

  }

  private PostRandomFetcher createPostRandomFetcher() {
    return new PostRandomFetcher();
  }

}
