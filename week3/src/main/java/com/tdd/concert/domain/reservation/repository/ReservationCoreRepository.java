package com.tdd.concert.domain.reservation.repository;

import com.tdd.concert.domain.concert.model.Concert;
import com.tdd.concert.domain.seat.model.Seat;
import com.tdd.concert.domain.reservation.model.Reservation;
import com.tdd.concert.domain.user.model.User;

public interface ReservationCoreRepository {

  public Reservation reserveSeat(Reservation reservation);

  public Reservation findReservationByUserIdAndSeatId(Long userId, Long seatId);

}
