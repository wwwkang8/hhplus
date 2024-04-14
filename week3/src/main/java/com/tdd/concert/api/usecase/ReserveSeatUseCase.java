package com.tdd.concert.api.usecase;

import com.tdd.concert.api.controller.dto.request.ReservationRequest;
import com.tdd.concert.api.controller.dto.response.ReservationResponse;
import com.tdd.concert.domain.concert.component.ConcertManager;
import com.tdd.concert.domain.concert.model.Concert;
import com.tdd.concert.domain.concert.model.ConcertSchedule;
import com.tdd.concert.domain.reservation.component.ReservationManager;
import com.tdd.concert.domain.reservation.model.Reservation;
import com.tdd.concert.domain.seat.component.SeatManager;
import com.tdd.concert.domain.seat.model.Seat;
import com.tdd.concert.domain.user.component.UserManager;
import com.tdd.concert.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReserveSeatUseCase {

  private final ReservationManager reservationManager;
  private final UserManager userManager;
  private final ConcertManager concertManager;
  private final SeatManager seatManager;


  /** 좌석 예약 */
  public ReservationResponse reserve(ReservationRequest request) {

    // 사용자를 조회한다.
    User user = userManager.findUserById(request.getUserId());
    if(user == null) {
      throw new RuntimeException("[좌석 예약] 존재하지 않는 사용자입니다.");
    }

    // 콘서트를 조회한다.
    Concert concert = concertManager.findConcertByConcertId(request.getConcertId());
    if(concert == null) {
      throw new RuntimeException("[좌석 예약] 존재하지  콘서트입니다.");
    }

    /** TODO 만약에 동시에 여러명이 이 좌석을 예약하려고 할 땐??
     * */
    Seat occupiedSeat = seatManager.occupy(request.getSeatNo(),
                                           concert.getConcertId(),
                                           request.getConcertDate(),
                                           user);

    ReservationRequest reservationRequest = new ReservationRequest(user, concert, occupiedSeat);

    Reservation reservation = reservationManager.reserve(reservationRequest);

    return ReservationResponse.from(reservation);
  }

}
