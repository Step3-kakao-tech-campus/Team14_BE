package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.outer.post.dto.SetPostLikeDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@Sql("classpath:db/teardown.sql")
@SpringBootTest
public class SchedulePostLikeCountUsecaseTest {

  @Autowired
  private SchedulePostLikeCountUsecase schedulePostLikeCountUsecase;

  @Autowired
  private SetPostLikeUsecase setPostLikeUseCase;

  @Test
  void execute() {
    SetPostLikeDTO setPostLikeDTO = new SetPostLikeDTO(1L,1L);
    setPostLikeUseCase.execute(setPostLikeDTO);

    schedulePostLikeCountUsecase.execute();


  }

}
