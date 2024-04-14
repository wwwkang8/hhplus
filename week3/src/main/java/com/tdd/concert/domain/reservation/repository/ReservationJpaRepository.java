package com.tdd.concert.domain.reservation.repository;

import com.tdd.concert.domain.reservation.model.Reservation;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReservationJpaRepository extends JpaRepository<Reservation, Long> {

  @Query("SELECT r FROM Reservation r WHERE r.user.userId = ?1 AND r.seat.seatId = ?2")
  public Reservation findReservationByUserIdAndSeatId(Long userId, Long seatId);

}
