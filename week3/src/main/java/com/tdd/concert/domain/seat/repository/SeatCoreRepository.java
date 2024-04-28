package com.tdd.concert.domain.seat.repository;

import java.time.LocalDate;
import java.util.List;

import com.tdd.concert.domain.concert.model.Concert;
import com.tdd.concert.domain.seat.model.Seat;
import com.tdd.concert.domain.seat.model.SeatStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface SeatCoreRepository {

  public Seat findSeatBySeatNoAndConcert(Long seatNo, Long concertId, LocalDate concertDate);

  public Seat findSeatBySeatNoWithExclusiveLock(Long seatNo, Long concertId, LocalDate concertDate);

  public List<Seat> findTempReservationExpiredSeatList(SeatStatus seatStatus);

  public Seat save(Seat seat);

}
