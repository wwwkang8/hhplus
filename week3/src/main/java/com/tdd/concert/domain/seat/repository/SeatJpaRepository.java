package com.tdd.concert.domain.seat.repository;

import com.tdd.concert.domain.concert.model.Concert;
import com.tdd.concert.domain.seat.model.Seat;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface SeatJpaRepository extends JpaRepository<Seat, Long> {

  public Seat findSeatBySeatNoAndConcert(Long seatNo, Concert concert);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("select s from Seat s where s.seatNo = ?1 AND s.concert.concertId = ?2")
  public Seat findSeatBySeatNoWithExclusiveLock(Long seatNo, Long concertId);

}
