package com.kakaotech.team14backend.inner.post.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Component
public class PostRandomFetcher {
  private static final ThreadLocalRandom random = ThreadLocalRandom.current();


  public Map<Integer, List<Integer>> fetchRandomIndexesForAllLevels(Map<Integer, Integer> levelCounts) {
    Map<Integer, List<Integer>> result = new HashMap<>();

    for (Map.Entry<Integer, Integer> entry : levelCounts.entrySet()) {
      int level = entry.getKey();
      int count = entry.getValue();
      result.put(level, fetchRandomIndexesForLevel(level, count));
    }

    return result;
  }

  private List<Integer> fetchRandomIndexesForLevel(int level, int count) {
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

    return generateRandomIndexes(start, end, count);
  }

  private List<Integer> generateRandomIndexes(int start, int end, int count) {
    Set<Integer> randomIndexesSet = new HashSet<>();

    while (randomIndexesSet.size() < count) {
      int randomIndex = start + random.nextInt(end - start + 1);
      randomIndexesSet.add(randomIndex);
    }

    List<Integer> sortedRandomIndexes = randomIndexesSet.stream()
        .sorted()
        .collect(Collectors.toList());

    return sortedRandomIndexes;
  }

  public Map<Integer, List<Integer>> fetchRandomIndexesUnder30ForAllLevels(Map<Integer, Integer> levelCounts, int size) {
    int totalSize = levelCounts.get(1) + levelCounts.get(2) + levelCounts.get(3);

    Set<Integer> randomIndexesSet = new HashSet<>();

    while (randomIndexesSet.size() < totalSize) {
      int randomIndex = 1 + random.nextInt(size);
      randomIndexesSet.add(randomIndex);
    }

    List<Integer> sortedRandomIndexes = randomIndexesSet.stream()
        .sorted()
        .collect(Collectors.toList());

    Map<Integer, List<Integer>> result = new HashMap<>();

    List<Integer> level3 = new ArrayList<>();
    List<Integer> level2 = new ArrayList<>();
    List<Integer> level1 = new ArrayList<>();

    for(int i=0; i<sortedRandomIndexes.size(); i++){
        if(sortedRandomIndexes.get(i) < PostLevel.LV3.end()){
          level3.add(sortedRandomIndexes.get(i));
        }else if(sortedRandomIndexes.get(i) < PostLevel.LV2.end()) {
          level2.add(sortedRandomIndexes.get(i));
        }else{
          level1.add(sortedRandomIndexes.get(i));
        }
    }

    if(!level3.isEmpty()){
      result.put(3, level3);
    }
    if (!level2.isEmpty()) {
      result.put(2, level2);
    }
    if (!level1.isEmpty()) {
      result.put(1, level1);
    }

    return result;
  }

}
