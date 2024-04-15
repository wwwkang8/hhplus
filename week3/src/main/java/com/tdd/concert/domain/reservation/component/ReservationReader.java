package com.tdd.concert.domain.reservation.component;

import com.tdd.concert.domain.reservation.model.Reservation;
import com.tdd.concert.domain.reservation.repository.ReservationCoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReservationReader {

  private final ReservationCoreRepository reservationCoreRepository;

  public Reservation findReservationByReservationId(Long userId, Long seatId) {
    return reservationCoreRepository.findReservationByUserIdAndSeatId(userId, seatId);
  }
}
