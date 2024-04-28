package com.tdd.concert.api.usecase;

import java.time.LocalDateTime;

import com.tdd.concert.api.controller.dto.request.PaymentRequest;
import com.tdd.concert.api.controller.dto.response.PaymentResponse;
import com.tdd.concert.api.controller.dto.response.TokenResponse;
import com.tdd.concert.domain.concert.component.ConcertManager;
import com.tdd.concert.domain.payment.component.PaymentManager;
import com.tdd.concert.domain.payment.model.Payment;
import com.tdd.concert.domain.reservation.component.ReservationManager;
import com.tdd.concert.domain.reservation.model.Reservation;
import com.tdd.concert.domain.reservation.model.ReservationStatus;
import com.tdd.concert.domain.seat.component.SeatManager;
import com.tdd.concert.domain.seat.model.Seat;
import com.tdd.concert.domain.seat.model.SeatStatus;
import com.tdd.concert.domain.token.component.TokenManager;
import com.tdd.concert.domain.token.component.TokenValidator;
import com.tdd.concert.domain.user.component.UserManager;
import com.tdd.concert.domain.user.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentUseCase {

  private final UserManager userManager;
  private final TokenValidator tokenValidator;
  private final TokenManager tokenManager;
  private final SeatManager seatManager;
  private final ReservationManager reservationManager;
  private final PaymentManager paymentManager;

  @Transactional
  public PaymentResponse payment(PaymentRequest request) {

    // 1. 유저 테이블에 존재하는 사용자인지 검증
    User user = userManager.findUserById(request.getUserId());
    if(user == null) {
      throw new RuntimeException("[PaymentUseCase] 존재하지 않는 사용자입니다. 사용자ID : "+request.getUserId());
    }

    // 2. 유효한 토큰인지 검증
    TokenResponse tokenResponse = tokenValidator.validateToken(user.getToken());

    // 3. 유효한 좌석번호인지 검증
    Seat seat = seatManager.findSeatBySeatNoAndConcert( request.getSeatNo(),
                                                        request.getConcertId(),
                                                        request.getConcertDate());

    // 4. 조회한 좌석이 Null이면 Exception
    if(seat == null) {
      throw new RuntimeException("[PaymentUseCase] 존재하지 않는 좌입니다. 좌석번호 : "+request.getSeatNo()
      + ", 콘서트아이디 : "+request.getConcertId()+", 콘서트일자 : "+request.getConcertDate());
    }

    // 5. 임시배정상태가 아닌 좌석이면 오류
    if(seat.getSeatStatus() == SeatStatus.AVAILABLE) {
      throw new RuntimeException("[PaymentUseCase] 임시배정상태가 아닌 좌석입니다. 좌석 예약을 먼저 해주세요.");
    }

    // 6. 이미 판매완료가 된 좌석이면 오류
    if(seat.getSeatStatus() == SeatStatus.SOLDOUT) {
      throw new RuntimeException("[PaymentUseCase] 이미 판매가 완료된 좌석입니다. 다른 좌석을 선택해주세요.");
    }

    // 7. 결제요청한 유저ID와 임시배정된 유저ID가 일치하는지 검증.
    if(seat.getTempReservedUserId() != request.getUserId()) {
      throw new RuntimeException("[PaymentUseCase] 임시배정된 사용자ID와 결제 요청한 사용자ID가 다릅니다.");
    }

    // 8. 좌석의 임시배정만료시각이 지났거나, 아예 null인지 검증
    if ((seat.getTempReservedExpiredAt() == null)
        || (seat.getTempReservedExpiredAt().isBefore(LocalDateTime.now()))) {
      throw new RuntimeException("[PaymentUseCase] 임시배정시각이 만료된 좌석입니다. 좌석 예약을 다시 해주세요.");
    }

    // 9. 좌석의 상태값을 SOLDOUT으로 변경
    seat.setSeatStatus(SeatStatus.SOLDOUT);

    // 10. 유저ID, 좌석ID를 사용해서 예약내역 조회. 조회한 예약내역의 상태값을 SUCCESS(예약성공)로 변경한다.
    Reservation reservation = reservationManager.findReservationByUserIdAndSeatId(user.getUserId(), seat.getSeatId());
    reservation.setReservationStatus(ReservationStatus.RESERVATION_SUCCESS);

    // 11. 결제 매니저로 포인트 차감하고, 결제내역 생성
    userManager.usePoint(user.getUserId(), seat.getPrice());
    Payment payment = paymentManager.payment(Payment.makePayment(seat.getPrice(), user, reservation));

    // 12. 해당 사용자의 토큰 만료처리
    tokenManager.expireToken(user.getToken());

    return PaymentResponse.from(payment);
  }


}
