package com.tdd.concert.domain.seat.repository;

import java.time.LocalDate;
import java.util.List;

import com.tdd.concert.domain.seat.model.Seat;
import com.tdd.concert.domain.seat.model.SeatStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface SeatJpaRepository extends JpaRepository<Seat, Long> {

  @Query("SELECT s FROM Seat s WHERE s.seatNo = ?1 AND s.concert.concertId = ?2 AND s.concertSchedule.concertDate = ?3")
  public Seat findSeatBySeatNoAndConcert(Long seatNo, Long concertId, LocalDate concertDate);

  @Query("SELECT s FROM Seat s WHERE s.seatNo = ?1 AND s.concert.concertId = ?2 AND s.concertSchedule.concertScheduleId = ?3")
  public Seat findSeatBySeatNoAndConcertAndConcertSchedule(Long seatNo, Long concertId, Long concertScheduleId);

  // 동시성 제어를 위한 비관적락
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("select s from Seat s where s.seatNo = ?1 AND s.concert.concertId = ?2 AND s.concertSchedule.concertDate = ?3")
  public Seat findSeatBySeatNoWithExclusiveLock(Long seatNo, Long concertId, LocalDate concertDate);


  @Query("SELECT s FROM Seat s WHERE s.seatStatus = ?1")
  public List<Seat> findTempReservationExpiredSeatList(SeatStatus seatStatus);

}
