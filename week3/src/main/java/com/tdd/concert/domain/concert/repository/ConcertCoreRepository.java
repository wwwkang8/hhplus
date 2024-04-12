package com.tdd.concert.domain.concert.repository;

import java.time.LocalDate;
import java.util.List;

import com.tdd.concert.domain.concert.model.Concert;
import com.tdd.concert.domain.concert.status.ReservationStatus;

public interface ConcertCoreRepository {

  public List<LocalDate> availableConcertDate(long concertId);

  public List<Long> seatNoList(long concertId, LocalDate concertDate, ReservationStatus reservationStatus);

  public Concert findConcertByConcertId(Long concertId);
}
