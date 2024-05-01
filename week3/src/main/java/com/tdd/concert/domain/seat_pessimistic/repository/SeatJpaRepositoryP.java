package com.tdd.concert.domain.seat_pessimistic.repository;

import java.time.LocalDate;
import java.util.List;

import com.tdd.concert.domain.seat_pessimistic.model.SeatP;
import com.tdd.concert.domain.seat_pessimistic.model.SeatStatusP;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface SeatJpaRepositoryP extends JpaRepository<SeatP, Long> {

  public SeatP findSeatPBySeatId(Long seatId);

  @Query("SELECT s FROM SeatP s WHERE s.seatNo = ?1 AND s.concert.concertId = ?2 AND s.concertSchedule.concertDate = ?3")
  public SeatP findSeatBySeatNoAndConcert(Long seatNo, Long concertId, LocalDate concertDate);

  // 동시성 제어를 위한 비관적락
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("select s from SeatP s where s.seatId = ?1")
  public SeatP findSeatBySeatNoWithExclusiveLock(Long seatId);


  @Query("SELECT s FROM SeatP s WHERE s.seatStatus = ?1")
  public List<SeatP> findTempReservationExpiredSeatList(SeatStatusP seatStatus);

}
