package com.kakaotech.team14backend.post.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

class PostRandomFetcherTest {

  @Test
  @DisplayName("limitSize가 30이상이고, 요구하는 게시글의 개수가 limitSize보다 작을 때, level1, level2, level3에 대한 랜덤 인덱스는 각각 3,3,4개가 나와야 한다.")
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
  @DisplayName("limitSize가 30보다 크고, 요구하는 게시글의 개수가 최대치이고, limitSize보다 작을 때, level1, level2, level3에 대한 랜덤 인덱스는 각각 9,9,9개가 나와야 한다.")
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
  @DisplayName("limitSize가 30보다 작고, 요구하는 총 개수의 합보다 클 경우, 응답하는 개시글의 총 개수는 5개가 나와야 한다." +
      " 또한 DB의 게시글의 개수 부족으로 프론트의 요구사항을 완벽히 맞추지 못할 때는 레벨이 높은 게시글의 요구사항부터 우선 충족시킨다.")
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
  @DisplayName("limitSize가 30보다 작고, 요구하는 총 개수의 합보다 작을 경우, 응답하는 개시글의 총 개수는 4개가 나와야 한다." +
      " 또한 DB의 게시글의 개수 부족으로 프론트의 요구사항을 완벽히 맞추지 못할 때는 레벨이 높은 게시글의 요구사항부터 우선 충족시킨다.")
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
  @DisplayName("limitSize가 30보다 작고, 요구하는 총 개수의 합보다 클 경우, 응답하는 게시글은 7개가 나와야 한다." +
      " 또한 DB의 게시글의 개수 부족으로 프론트의 요구사항을 완벽히 맞추지 못할 때는 레벨이 높은 게시글의 요구사항부터 우선 충족시킨다." +
      " 따라서, level 3 게시글 4개, level2 게시글 3개가 나와야한다.")
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
