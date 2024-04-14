package com.tdd.concert.domain.reservation.component;

import com.tdd.concert.api.controller.dto.request.ReservationRequest;
import com.tdd.concert.api.controller.dto.request.TokenRequest;
import com.tdd.concert.domain.concert.component.ConcertManager;
import com.tdd.concert.domain.concert.model.Concert;
import com.tdd.concert.domain.seat.component.SeatManager;
import com.tdd.concert.domain.seat.model.Seat;
import com.tdd.concert.domain.seat.model.SeatStatus;
import com.tdd.concert.domain.token.component.TokenManager;
import com.tdd.concert.domain.user.component.UserManager;
import com.tdd.concert.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReservationValidator {

  private final UserManager userManager;
  private final ConcertManager concertManager;
  private final TokenManager tokenManager;
  private final SeatManager seatManager;

  public boolean validate(ReservationRequest request) {
    log.info("[ReservationValidator] validate 메서드 시작");

    // 1. 사용자 조회
    User user = userManager.findUserById(request.getUser().getUserId());

    if(user == null) {
      throw new RuntimeException("존재하지 않는 사용자입니다." );
    }

    // 2. 유효한 토큰인지 검증
    TokenRequest tokenRequest = new TokenRequest(user.getToken());
    tokenManager.validateToken(tokenRequest);

    // 3. 콘서트 아이디, 좌석 번호 검증
    Concert concert = concertManager.findConcertByConcertId(request.getConcert().getConcertId());

    if(concert == null) {
      throw new RuntimeException("존재하지 않는 콘서트입니다.");
    }

    // 4.좌석 번호 검증
    //Seat seat = seatManager.findSeatBySeatNoAndConcert(request.getSeat().getSeatNo(), concert);

//    if(seat == null) {
//      throw new RuntimeException("존재하지 않는 좌석번호입니다.");
//    }


    return true;
  }
}
