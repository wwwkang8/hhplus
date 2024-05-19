package com.tdd.concert.domain.reservation.infra;

import com.tdd.concert.domain.reservation.model.Reservation;
import com.tdd.concert.domain.reservation.repository.ReservationCoreRepository;
import com.tdd.concert.domain.reservation.repository.ReservationJpaRepository;
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

  @Override
  public Reservation findReservationByReservationId(Long reservationId) {
    return reservationJpaRepository.findReservationByReservationId(reservationId);
  }
}
