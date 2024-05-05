package com.tdd.concert.domain.seat_distribute.repository;

import java.time.LocalDate;
import java.util.List;

import com.tdd.concert.domain.seat_distribute.model.SeatD;
import com.tdd.concert.domain.seat_distribute.model.SeatStatusD;

public interface SeatCoreRepositoryD {

  public SeatD findSeatBySeatNoAndConcert(Long seatNo, Long concertId, LocalDate concertDate);

  public SeatD findSeatDBySeatId(Long seatId);

  public List<SeatD> findTempReservationExpiredSeatList(SeatStatusD seatStatus);

}
