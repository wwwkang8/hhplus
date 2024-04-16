package com.tdd.concert.api.usecase;

import com.tdd.concert.domain.concert.component.ConcertManager;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConcertDateIntegrationTest {

  @Autowired
  private ConcertDateUseCase concertDateUseCase;

  @Autowired
  private ConcertManager concertManager;



}
