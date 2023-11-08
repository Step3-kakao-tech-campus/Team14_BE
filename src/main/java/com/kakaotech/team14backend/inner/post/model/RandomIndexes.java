package com.kakaotech.team14backend.inner.post.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class RandomIndexes {
  private final List<Integer> indexes;

  public RandomIndexes(int start, int end, int size) {
    List<Integer> randomIndexes = generateRandomIndexes(start, end, size);
    this.indexes = randomIndexes;
  }

  public RandomIndexes(){
    this.indexes = List.of();
  }
  public List<Integer> getIndexes() {
    return indexes;
  }

  private List<Integer> generateRandomIndexes(int start, int end, int size) {
    Set<Integer> randomIndexes = new HashSet<>();

    while (randomIndexes.size() < size) {
      if(randomIndexes.size() == (end - start)){
        break;
      }
      int randomIndex = start + ThreadLocalRandom.current().nextInt(end - start + 1);
      randomIndexes.add(randomIndex);
    }

    List<Integer> sortedRandomIndexes = randomIndexes.stream()
        .sorted()
        .collect(Collectors.toList());

    return sortedRandomIndexes;
  }
}
