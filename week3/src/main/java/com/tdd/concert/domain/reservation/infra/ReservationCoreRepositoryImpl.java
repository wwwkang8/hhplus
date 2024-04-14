package com.tdd.concert.domain.reservation.infra;

import com.tdd.concert.domain.concert.model.Concert;
import com.tdd.concert.domain.seat.model.Seat;
import com.tdd.concert.domain.reservation.model.Reservation;
import com.tdd.concert.domain.reservation.repository.ReservationCoreRepository;
import com.tdd.concert.domain.reservation.repository.ReservationJpaRepository;
import com.tdd.concert.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ReservationCoreRepositoryImpl implements ReservationCoreRepository {

  private final ReservationJpaRepository reservationJpaRepository;

  @Override
  public Reservation reserveSeat(Reservation reservation) {

    return reservationJpaRepository.save(reservation);
  }

  @Override
  public Reservation findReservationByUserIdAndSeatId(Long userId, Long seatId) {
    return reservationJpaRepository.findReservationByUserIdAndSeatId(userId, seatId);
  }
}
