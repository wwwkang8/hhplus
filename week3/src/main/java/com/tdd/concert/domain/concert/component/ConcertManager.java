package com.tdd.concert.domain.concert.component;


import java.time.LocalDate;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConcertManager {

  private final ConcertReader concertReader;
  private final ConcertWriter concertWriter;

  public List<LocalDate> availableConcertDate(long concertId) {
    return concertReader.availableConcertDate(concertId);
  }


}
