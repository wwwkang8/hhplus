package com.tdd.concert.api.concurrency;

import com.tdd.concert.api.controller.dto.request.ReservationRequest;
import com.tdd.concert.api.controller.dto.response.ReservationResponse;
import com.tdd.concert.domain.concert.component.ConcertManager;
import com.tdd.concert.domain.concert.model.Concert;
import com.tdd.concert.domain.reservation.component.ReservationManager;
import com.tdd.concert.domain.reservation.model.Reservation;
import com.tdd.concert.domain.seat_optimistic.component.SeatManagerO;
import com.tdd.concert.domain.seat_optimistic.model.SeatO;
import com.tdd.concert.domain.user.component.UserManager;
import com.tdd.concert.domain.user.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReserveSeatOptimisticLock {

  /**
   * 동시성 테스트를 위한 UseCase
   * 락 종류 : 낙관적 락
   * 성능 :
   * 효율성 :
   * */

  private final ReservationManager reservationManager;
  private final UserManager userManager;
  private final ConcertManager concertManager;
  private final SeatManagerO seatManagerO;

  /** 좌석 예약 */
  @Transactional
  public ReservationResponse reserve(ReservationRequest request) {

    log.info("[ReserveSeatOptimisticLock] 예약 시작");

    // 1. 사용자를 조회한다.
    User user = userManager.findUserById(request.getUserId());
    if(user == null) {
      throw new RuntimeException("[좌석 예약] 존재하지 않는 사용자입니다.");
    }
    log.info("[ReserveSeatOptimisticLock] 사용자 검증 완료");

    // 2. 콘서트를 조회한다.
    Concert concert = concertManager.findConcertByConcertId(request.getConcertId());
    if(concert == null) {
      throw new RuntimeException("[좌석 예약] 존재하지  콘서트입니다.");
    }
    log.info("[ReserveSeatOptimisticLock] 콘서트 조회완료");

    // 3. 좌석을 임시배정한다.
    SeatO occupiedSeat = seatManagerO.occupy(request.getSeatO().getSeatNo(),
                                              concert.getConcertId(),
                                              request.getConcertDate(),
                                              user);

    log.info("[ReserveSeatOptimisticLock] 좌석 Occupy 완료");

    ReservationRequest reservationRequest = new ReservationRequest(user, concert, occupiedSeat);

    Reservation reservation = reservationManager.reserve(reservationRequest);

    return ReservationResponse.fromO(reservation.getConcert().getConcertId(),
                                    reservation.getUser().getUserId(),
                                    occupiedSeat.getSeatNo(),
                                    occupiedSeat.getTempReservedExpiredAt());

  }

}
