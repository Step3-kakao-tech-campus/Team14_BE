package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.point.domain.UsePointDecider;
import com.kakaotech.team14backend.post.dto.PostLevelPoint;
import com.kakaotech.team14backend.post.application.GetPopularPostPoint;
import com.kakaotech.team14backend.post.application.SavePopularPosts;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.EnabledIf;


@SpringBootTest
@EnabledIf(value = "#{environment.getActiveProfiles()[0] == 'local'}", loadContext = true)
@Sql(value = "classpath:db/testSetup.sql")
class GetPopularPostPointUsecaseTest {

  @Autowired
  private GetPopularPostPoint getPopularPostPointUsecase;

  @Autowired
  private SavePopularPosts saveTemporaryPopularPostListUsecase;

  @BeforeEach
  void setUp() {
    saveTemporaryPopularPostListUsecase.execute();
  }

  @Test
  void execute(){
    PostLevelPoint postLevelPoint = getPopularPostPointUsecase.execute(188L);
    Integer level = postLevelPoint.postLevel();
    Long point = UsePointDecider.decidePoint(level);
    Assertions.assertThat(point).isEqualTo(postLevelPoint.postPoint());
  }


}
