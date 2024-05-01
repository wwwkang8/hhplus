package com.tdd.concert.domain.seat_optimistic.repository;

import java.time.LocalDate;
import java.util.List;

import com.tdd.concert.domain.seat_optimistic.model.SeatO;
import com.tdd.concert.domain.seat_optimistic.model.SeatStatusO;

public interface SeatCoreRepositoryO {

  public SeatO findSeatOBySeatId(Long seatId);

  public SeatO findSeatBySeatNoWithOptimisticLock(Long seatNo, Long concertId, LocalDate concertDate);

  public List<SeatO> findTempReservationExpiredSeatList(SeatStatusO seatStatus);

  public SeatO save(SeatO seat);

}
