package com.tdd.concert.domain.reservation.repository;

import com.tdd.concert.domain.reservation.model.Reservation;

public interface ReservationCoreRepository {

  public Reservation reserveSeat(Reservation reservation);

  public Reservation findReservationByUserIdAndSeatId(Long userId, Long seatId);

  public Reservation findReservationByReservationId(Long reservationId);

}
