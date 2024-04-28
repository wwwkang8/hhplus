package com.tdd.concert.domain.reservation.component;

import com.tdd.concert.api.controller.dto.request.ReservationRequest;
import com.tdd.concert.domain.reservation.model.Reservation;
import com.tdd.concert.domain.reservation.repository.ReservationCoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReservationStore {

  private final ReservationCoreRepository reservationCoreRepository;


  @Transactional
  public Reservation reserveSeat(Reservation reservation) {
    return reservationCoreRepository.reserveSeat(reservation);
  }


}
