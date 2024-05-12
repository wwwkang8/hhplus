package com.tdd.concert.domain.concert.repository;

import java.time.LocalDate;
import java.util.List;

import com.tdd.concert.domain.concert.model.Concert;
import com.tdd.concert.domain.seat.model.SeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ConcertJpaRepository extends JpaRepository<Concert, Long> {

  @Query("SELECT cs.concertDate FROM ConcertSchedule cs JOIN cs.concert c WHERE c.concertId = :concertId")
  public List<LocalDate> availableConcertSchedule(@Param("concertId") long concertId);

  @Query("SELECT s.seatNo FROM Seat s JOIN s.concertSchedule cs WHERE cs.concert.concertId = :concertId" +
      " AND cs.concertDate = :concertDate" +
      " AND s.seatStatus = :seatStatus" +
      " ORDER BY s.seatNo ASC")
  List<Long> seatNoList(@Param("concertId") long concertId,
                        @Param("concertDate") LocalDate concertDate,
                        @Param("seatStatus") SeatStatus seatStatus);

  public Concert findConcertByConcertId(Long concertId);

  @Query("SELECT c.concertId FROM Concert c")
  public List<Long> concertIdList();
}
