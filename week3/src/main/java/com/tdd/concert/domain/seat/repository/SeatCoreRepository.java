package com.tdd.concert.domain.seat.repository;

import java.time.LocalDate;
import java.util.List;

import com.tdd.concert.domain.seat.model.Seat;
import com.tdd.concert.domain.seat.model.SeatStatus;

public interface SeatCoreRepository {

  public Seat findSeatBySeatNoAndConcert(Long seatNo, Long concertId, LocalDate concertDate);

  public Seat findSeatBySeatNoWithExclusiveLock(Long seatNo, Long concertId, LocalDate concertDate);

  public Seat findSeatBySeatIdWithExclusiveLock(Long seatId);

  public List<Seat> findTempReservationExpiredSeatList(SeatStatus seatStatus);

  public Seat save(Seat seat);

}
