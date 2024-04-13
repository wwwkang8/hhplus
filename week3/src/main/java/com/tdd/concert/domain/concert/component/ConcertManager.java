package com.tdd.concert.domain.concert.component;


import java.time.LocalDate;
import java.util.List;

import com.tdd.concert.domain.concert.model.Concert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConcertManager {

  private final ConcertReader concertReader;
  private final ConcertWriter concertWriter;

  public List<LocalDate> availableConcertSchedule(long concertId) {
    return concertReader.availableConcertSchedule(concertId);
  }


  public List<Long> seatNoList(long concertId, LocalDate concertDate) {
    return concertReader.seatNoList(concertId, concertDate);
  }

  public Concert findConcertByConcertId(Long concertId) {
    return concertReader.findConcertByConcertId(concertId);
  }
}
