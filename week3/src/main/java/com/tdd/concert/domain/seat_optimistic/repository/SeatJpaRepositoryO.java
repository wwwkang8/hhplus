package com.tdd.concert.domain.seat_optimistic.repository;

import java.time.LocalDate;
import java.util.List;

import com.tdd.concert.domain.seat_optimistic.model.SeatO;
import com.tdd.concert.domain.seat_optimistic.model.SeatStatusO;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface SeatJpaRepositoryO extends JpaRepository<SeatO, Long> {

  public SeatO findSeatOBySeatId(Long seatId);

  // 동시성 제어를 위한 낙관적락
  @Lock(LockModeType.OPTIMISTIC)
  @Query("select s from SeatO s where s.seatNo = ?1 AND s.concert.concertId = ?2 AND s.concertSchedule.concertDate = ?3")
  public SeatO findSeatBySeatNoWithOptimisticLock(Long seatNo, Long concertId, LocalDate concertDate);


  @Query("SELECT s FROM SeatO s WHERE s.seatStatusO = ?1")
  public List<SeatO> findTempReservationExpiredSeatList(SeatStatusO seatStatus);

}
