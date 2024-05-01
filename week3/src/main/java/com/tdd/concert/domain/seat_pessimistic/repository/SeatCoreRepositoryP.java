package com.tdd.concert.domain.seat_pessimistic.repository;

import java.time.LocalDate;
import java.util.List;

import com.tdd.concert.domain.seat_pessimistic.model.SeatP;
import com.tdd.concert.domain.seat_pessimistic.model.SeatStatusP;

public interface SeatCoreRepositoryP {

  public SeatP findSeatP(Long seatId);

  public SeatP findSeatBySeatNoAndConcert(Long seatNo, Long concertId, LocalDate concertDate);

  public SeatP findSeatBySeatNoWithExclusiveLock(Long seatId);

  public List<SeatP> findTempReservationExpiredSeatList(SeatStatusP seatStatus);

  public SeatP save(SeatP seat);

}
