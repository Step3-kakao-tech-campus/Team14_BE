package com.kakaotech.team14backend.inner.post.model;

import org.springframework.stereotype.Component;

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
        start = PostLevel.LV3_RANGE_START.getValue();
        end = PostLevel.LV3_RANGE_END.getValue();
        break;
      case 2:
        start = PostLevel.LV2_RANGE_START.getValue();
        end = PostLevel.LV2_RANGE_END.getValue();
        break;
      case 1:
        start = PostLevel.LV1_RANGE_START.getValue();
        end = PostLevel.LV1_RANGE_END.getValue();
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

}
