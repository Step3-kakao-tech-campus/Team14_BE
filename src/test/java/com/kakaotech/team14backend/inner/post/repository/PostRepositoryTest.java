package com.kakaotech.team14backend.inner.post.repository;

import com.kakaotech.team14backend.outer.post.dto.GetIncompletePopularPostDTO;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.EnabledIf;


@EnabledIf(value = "#{environment.getActiveProfiles()[0] == 'local'}", loadContext = true)

@Sql(value = "classpath:db/teardown.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SpringBootTest
class PostRepositoryTest {

  @Autowired
  private PostRepository postRepository;

  @Test
  void findTop300ByOrderByPopularityDesc() {
    List<GetIncompletePopularPostDTO> top300posts = postRepository.findTop300ByOrderByPopularityDesc(
        PageRequest.of(0, 300));
    System.out.println(top300posts);
    Assertions.assertThat(top300posts.size()).isEqualTo(300);
  }


}
