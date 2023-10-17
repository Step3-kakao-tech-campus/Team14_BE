package com.kakaotech.team14backend.inner.post.study;

import com.kakaotech.team14backend.inner.post.model.PostRandomFetcher;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RandomTest {

  @Test
  void test(){
    // 동시 요청을 처리할 스레드 풀 생성
    int numThreads = 10; // 원하는 스레드 수
    ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

    // 여러 요청을 동시에 처리하는 작업을 생성
    for (int i = 0; i < 100; i++) {
      int finalI = i;
      executorService.submit(() -> {
        PostRandomFetcher postRandomFetcher = new PostRandomFetcher();
        Map<Integer, Integer> levelCounts = new HashMap<>();
        levelCounts.put(3, 4);
        levelCounts.put(2, 3);
        levelCounts.put(1, 3);
        Map<Integer, List<Integer>> levelOfIndexes = postRandomFetcher.fetchRandomIndexesForAllLevels(levelCounts);

        System.out.println(finalI +"번째 인덱스 : " + levelOfIndexes.get(1));
        System.out.println(finalI +"번째 인덱스 : " + levelOfIndexes.get(2));
        System.out.println(finalI +"번째 인덱스 : " + levelOfIndexes.get(3));
      });
    }

    // 작업이 완료될 때까지 대기
    executorService.shutdown();

  }

  @Test
  void test2(){
    Random random = new Random();
  }
}
