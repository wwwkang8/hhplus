package com.tdd.concert.domain.concert.repository;

import java.time.LocalDate;
import java.util.List;

import com.tdd.concert.domain.concert.model.Concert;
import com.tdd.concert.domain.concert.status.ReservationStatus;
import com.tdd.concert.domain.token.status.ProgressStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ConcertJpaRepository extends JpaRepository<Concert, Long> {

  @Query("SELECT c.concertDate FROM Concert c WHERE c.concertId = :concertId")
  public List<LocalDate> availableConcertDate(@Param("concertId") long concertId);

  @Query("SELECT s.seatNo FROM Seat s JOIN s.concert c WHERE c.concertId = :concertId" +
      " AND c.concertDate = :concertDate" +
      " AND s.reservationStatus = :reservationStatus")
  List<Long> seatNoList(@Param("concertId") long concertId,
                        @Param("concertDate") LocalDate concertDate,
                        @Param("reservationStatus")ReservationStatus reservationStatus);

  public Concert findConcertByConcertId(Long concertId);
}
