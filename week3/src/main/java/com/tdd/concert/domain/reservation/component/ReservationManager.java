package com.tdd.concert.domain.reservation.component;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.tdd.concert.api.controller.dto.request.ReservationRequest;
import com.tdd.concert.domain.concert.component.ConcertManager;
import com.tdd.concert.domain.concert.model.Concert;
import com.tdd.concert.domain.concert.status.ReservationStatus;
import com.tdd.concert.domain.reservation.model.Reservation;
import com.tdd.concert.domain.seat.component.SeatManager;
import com.tdd.concert.domain.seat.model.Seat;
import com.tdd.concert.domain.user.component.UserManager;
import com.tdd.concert.domain.user.model.User;
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
  private final UserManager userManager;
  private final ConcertManager concertManager;
  private final SeatManager seatManager;


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

    // 사용자를 조회한다.
    log.info("[사용자 조회 전]");
    User user = userManager.findUserById(request.getUserId());
    log.info("[사용자 조회 전]");

    // 콘서트를 조회한다.
    log.info("[콘서트 조회 직전]");
    Concert concert = concertManager.findConcertByConcertId(request.getConcertId());
    log.info("[콘서트 조회 직후]");

    // 좌석번호에 대한 좌석을 조회한다.
    /** TODO 만약에 동시에 여러명이 이 좌석을 예약하려고 할 땐??
     * 동시성에 대한 제어가 필요해보인다.
     * */
    log.info("[좌석예약 직전]");
    Seat occupiedSeat = seatManager.occupy(request.getSeatNo(), request.getConcertId(), user);
    log.info("[좌석예약 직후]");

    // Reservation 도메인에 있는 makeReservation 메서드로 Reservation을 조합
    Reservation reservation = Reservation.makeReservation(user, concert, occupiedSeat);

    log.info("[ReservationManager] reserve 메서드 종료");

    return reservationStore.reserveSeat(reservation);
  }




}
