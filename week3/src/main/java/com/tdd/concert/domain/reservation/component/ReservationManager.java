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


  /**
   * 예약 요건
   * 1. 좌석정보, 사용자아이디, 콘서트정보, 날짜 받아온다
   * 2. 좌석정보 => 해당 좌석 임시만료시간, 사용자 아이디 세팅
   * 3. 콘서트정보, 날짜 유효한지 확인한다.
   * 4.
   * */

  @Transactional
  public Reservation reserve(ReservationRequest request) {
    log.info("[ReservationManager] reserve 메서드 시작");

    // 좌석예약에 대한 검증(사용자ID, 콘서트, 좌석예약상태 검증)
    reservationValidator.validate(request);

    // Reservation 도메인에 있는 makeReservation 메서드로 Reservation을 조합
    Reservation reservation = Reservation.makeReservation(request.getUser(),
                                                          request.getConcert(),
                                                          request.getSeat(),
                                                          ReservationStatus.TEMPORARY_RESERVED);

    return reservationStore.reserveSeat(reservation);
  }




}
