package com.tdd.concert.domain.seat.repository;

import com.tdd.concert.domain.concert.model.Concert;
import com.tdd.concert.domain.seat.model.Seat;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface SeatCoreRepository {

  public Seat findSeatBySeatNoAndConcert(Long seatNo, Concert concert);

  public Seat findSeatBySeatNoWithExclusiveLock(Long seatNo, Long concertId);

  public Seat save(Seat seat);

}
