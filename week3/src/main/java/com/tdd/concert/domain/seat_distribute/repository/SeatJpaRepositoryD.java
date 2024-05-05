package com.tdd.concert.domain.seat_distribute.repository;

import java.time.LocalDate;
import java.util.List;

import com.tdd.concert.domain.seat_distribute.model.SeatD;
import com.tdd.concert.domain.seat_distribute.model.SeatStatusD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SeatJpaRepositoryD extends JpaRepository<SeatD, Long> {

  @Query("SELECT s FROM SeatD s WHERE s.seatNo = ?1 AND s.concertId = ?2 AND s.concertSchedule = ?3")
  public SeatD findSeatBySeatNoAndConcert(Long seatNo, Long concertId, LocalDate concertDate);

  @Query("select s from SeatD s where s.seatId = ?1")
  public SeatD findSeatDBySeatId(Long seatId);


  @Query("SELECT s FROM SeatD s WHERE s.seatStatusD = ?1")
  public List<SeatD> findTempReservationExpiredSeatList(SeatStatusD seatStatus);

}
