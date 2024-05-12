package com.tdd.concert.domain.concert.repository;

import java.time.LocalDate;
import java.util.List;

import com.tdd.concert.domain.concert.model.Concert;
import com.tdd.concert.domain.seat.model.SeatStatus;

public interface ConcertCoreRepository {

  public List<Long> concertList();

  public List<LocalDate> availableConcertSchedule(long concertId);

  public List<Long> seatNoList(long concertId, LocalDate concertDate, SeatStatus seatStatus);

  public Concert findConcertByConcertId(Long concertId);
}
