package com.tdd.concert.domain.reservation.component;

import com.tdd.concert.api.controller.dto.request.ReservationRequest;
import com.tdd.concert.domain.reservation.model.Reservation;
import com.tdd.concert.domain.reservation.model.ReservationStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReservationManager {

  private final ReservationStore reservationStore;
  private final ReservationReader reservationReader;
  private final ReservationValidator reservationValidator;


  /** 좌석 예약 */
  @Transactional
  public Reservation reserve(ReservationRequest request) {
    log.info("[ReservationManager] reserve 메서드 진입");

    // 좌석예약에 대한 검증(사용자ID, 콘서트, 좌석예약상태 검증)
    reservationValidator.validate(request);

    // Reservation 도메인에 있는 makeReservation 메서드로 Reservation을 조합
    Reservation reservation = Reservation.makeReservation(request.getUser(),
                                                          request.getConcert(),
                                                          request.getSeat(),
                                                          ReservationStatus.TEMPORARY_RESERVED);

    return reservationStore.reserveSeat(reservation);
  }

  public Reservation findReservationByUserIdAndSeatId(Long userId, Long seatId) {
    return reservationReader.findReservationByUserIdAndSeatId(userId, seatId);
  }




}
