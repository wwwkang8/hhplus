package com.tdd.concert.domain.seat.repository;

import java.time.LocalDate;

import com.tdd.concert.domain.concert.model.Concert;
import com.tdd.concert.domain.seat.model.Seat;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface SeatCoreRepository {

  public Seat findSeatBySeatNoAndConcert(Long seatNo, Long concertId, LocalDate concertDate);

  public Seat findSeatBySeatNoWithExclusiveLock(Long seatNo, Long concertId, LocalDate concertDate);

  public Seat save(Seat seat);

}
