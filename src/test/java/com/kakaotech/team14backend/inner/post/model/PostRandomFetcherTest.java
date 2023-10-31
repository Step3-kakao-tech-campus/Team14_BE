package com.kakaotech.team14backend.inner.post.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class PostRandomFetcherTest {

  private PostRandomFetcher postRandomFetcher;

  @BeforeEach
  void setUp() {

    postRandomFetcher = createPostRandomFetcher();
  }

  @Test
  void fetchRandomIndexesForAllLevels() {
    Map<Integer, Integer> levelSize = new HashMap<>();
    levelSize.put(1, 3);
    levelSize.put(2, 3);
    levelSize.put(3, 4);

    Map<Integer, List<Integer>> integerListMap = postRandomFetcher.fetchRandomIndexesForAllLevels(levelSize);
    for(Map.Entry<Integer, List<Integer>> entry : integerListMap.entrySet()){
      System.out.println("key : " + entry.getKey() + " value : " + entry.getValue());
    }

    Assertions.assertEquals(3, integerListMap.get(1).size());
    Assertions.assertEquals(3, integerListMap.get(2).size());
    Assertions.assertEquals(4, integerListMap.get(3).size());
  }

  @Test
  void fetchRandomIndexesForAllLevels_max_size() {
    Map<Integer, Integer> levelSize = new HashMap<>();
    levelSize.put(1, 9);
    levelSize.put(2, 9);
    levelSize.put(3, 9);

    Map<Integer, List<Integer>> integerListMap = postRandomFetcher.fetchRandomIndexesForAllLevels(levelSize);
    for(Map.Entry<Integer, List<Integer>> entry : integerListMap.entrySet()){
      System.out.println("key : " + entry.getKey() + " value : " + entry.getValue());
    }

    Assertions.assertEquals(9, integerListMap.get(1).size());
    Assertions.assertEquals(9, integerListMap.get(2).size());
    Assertions.assertEquals(9, integerListMap.get(3).size());
  }

  @Test
  void fetchRandomIndexesUnder30ForAllLevels_20() {
    Map<Integer, Integer> levelSize = new HashMap<>();
    levelSize.put(1, 3);
    levelSize.put(2, 3);
    levelSize.put(3, 4);


    int totalSize = 0;
    Map<Integer, List<Integer>> integerListMap = postRandomFetcher.fetchRandomIndexesUnder30ForAllLevels(levelSize, 20);
    for(Map.Entry<Integer, List<Integer>> entry : integerListMap.entrySet()){
      System.out.println("key : " + entry.getKey() + " value : " + entry.getValue());
      totalSize += entry.getValue().size();
    }

    Assertions.assertEquals(10, totalSize);

  }

  @Test
  void fetchRandomIndexesUnder30ForAllLevels_8() {
    Map<Integer, Integer> levelSize = new HashMap<>();
    levelSize.put(1, 3);
    levelSize.put(2, 3);
    levelSize.put(3, 4);


    int totalSize = 0;
    Map<Integer, List<Integer>> integerListMap = postRandomFetcher.fetchRandomIndexesUnder30ForAllLevels(levelSize, 8);
    for(Map.Entry<Integer, List<Integer>> entry : integerListMap.entrySet()){
      System.out.println("key : " + entry.getKey() + " value : " + entry.getValue());
      totalSize += entry.getValue().size();
    }

    Assertions.assertEquals(8, totalSize);

  }



  private PostRandomFetcher createPostRandomFetcher() {
    return new PostRandomFetcher();
  }

}
