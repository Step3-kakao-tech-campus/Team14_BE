package com.kakaotech.team14backend.post.infrastructure;

import com.kakaotech.team14backend.post.dto.GetIncompletePopularPostDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.EnabledIf;

import java.util.List;


@EnabledIf(value = "#{environment.getActiveProfiles()[0] == 'local'}", loadContext = true)

@Sql(value = "classpath:db/testSetup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
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
