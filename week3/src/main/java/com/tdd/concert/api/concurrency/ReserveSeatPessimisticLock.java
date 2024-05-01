package com.tdd.concert.api.concurrency;

import com.tdd.concert.api.controller.dto.request.ReservationRequest;
import com.tdd.concert.api.controller.dto.response.ReservationResponse;
import com.tdd.concert.domain.concert.component.ConcertManager;
import com.tdd.concert.domain.concert.model.Concert;
import com.tdd.concert.domain.reservation.component.ReservationManager;
import com.tdd.concert.domain.reservation.model.Reservation;
import com.tdd.concert.domain.seat_pessimistic.component.SeatManagerP;
import com.tdd.concert.domain.seat_pessimistic.model.SeatP;
import com.tdd.concert.domain.user.component.UserManager;
import com.tdd.concert.domain.user.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReserveSeatPessimisticLock {

  /**
   * 동시성 테스트를 위한 UseCase
   * 락 종류 : 비관적 락
   * 성능 :
   * 효율성 :
   * */

  private final ReservationManager reservationManager;
  private final UserManager userManager;
  private final ConcertManager concertManager;
  private final SeatManagerP seatManagerP;


  /** 좌석 예약 */
  @Transactional
  public ReservationResponse reserve(ReservationRequest request) {
    log.info("[ReserveSeatUseCase] 예약 시작");

    // 1. 사용자를 조회한다.
    User user = userManager.findUserById(request.getUserId());
    if(user == null) {
      throw new RuntimeException("[좌석 예약] 존재하지 않는 사용자입니다.");
    }
    log.info("[ReserveSeatUseCase] 사용자 검증 완료");

    Concert concert = Concert.builder().concertId(1L).build();


    SeatP seatP = seatManagerP.findSeatBySeatNoAndConcert(request.getSeatNo(), request.getConcertId(), request.getConcertDate());

    // 3. 좌석을 임시배정한다.
    SeatP occupiedSeat = seatManagerP.occupy(seatP, user);

    log.info("[ReserveSeatUseCase] 좌석 Occupy 완료");

    ReservationRequest reservationRequest = new ReservationRequest(user, concert, occupiedSeat);

    Reservation reservation = reservationManager.reserve(reservationRequest);

    return ReservationResponse.from(reservation);
  }

}