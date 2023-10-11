package com.kakaotech.team14backend.inner.post.repository;

import com.kakaotech.team14backend.outer.post.dto.GetIncompletePopularPostDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import java.util.List;



@Sql(value = "classpath:db/teardown.sql")
@DataJpaTest
class PostRepositoryTest {

  @Autowired
  private PostRepository postRepository;

  @Test
  void findTop300ByOrderByPopularityDesc() {
    List<GetIncompletePopularPostDTO> top300ByOrderByPopularityDesc = postRepository.findTop300ByOrderByPopularityDesc(PageRequest.of(0, 300));
    System.out.println(top300ByOrderByPopularityDesc);
    Assertions.assertThat(top300ByOrderByPopularityDesc.size()).isEqualTo(300);
  }


}
