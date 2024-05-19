package com.tdd.concert.api.payment_transaction_separation.tx1;

import java.time.LocalDateTime;

import com.tdd.concert.api.controller.dto.request.PaymentRequest;
import com.tdd.concert.api.controller.dto.response.PaymentResponse;
import com.tdd.concert.api.controller.dto.response.TokenResponse;
import com.tdd.concert.api.payment_transaction_separation.event.ChangeStatusEvent;
import com.tdd.concert.api.payment_transaction_separation.event.OutboxEvent;
import com.tdd.concert.domain.payment.component.PaymentManager;
import com.tdd.concert.domain.payment.model.Payment;
import com.tdd.concert.domain.reservation.component.ReservationManager;
import com.tdd.concert.domain.reservation.model.Reservation;
import com.tdd.concert.domain.seat.component.SeatManager;
import com.tdd.concert.domain.seat.model.Seat;
import com.tdd.concert.domain.seat.model.SeatStatus;
import com.tdd.concert.domain.token.component.TokenValidator;
import com.tdd.concert.domain.user.component.UserManager;
import com.tdd.concert.domain.user.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreatePayment_Tx1 {

  private final UserManager userManager;
  private final TokenValidator tokenValidator;
  private final PaymentManager paymentManager;
  private final SeatManager seatManager;
  private final ReservationManager reservationManager;
  private final ApplicationEventPublisher applicationEventPublisher;

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

    Reservation reservation = reservationManager.findReservationByUserIdAndSeatId(user.getUserId(), seat.getSeatId());
    Payment payment = paymentManager.payment(Payment.makePayment(seat.getPrice(), user, reservation));


    // Outbox 테이블에 메시지 insert 한다
    OutboxEvent outboxEvent = new OutboxEvent(this, "CHANGE_STATUS",user.getUserId(), seat.getSeatId(), reservation.getReservationId());
    applicationEventPublisher.publishEvent(outboxEvent);

    ChangeStatusEvent changeStatusEvent = new ChangeStatusEvent(this, "NORMAL",seat.getSeatId(), reservation.getReservationId() );
    applicationEventPublisher.publishEvent(changeStatusEvent);

    return PaymentResponse.from(payment);
  }

  @Transactional
  public PaymentResponse paymentRollBack(Long userId, Long seatId, Long reservationId) {

    log.info("payment 롤백");


    return null;
  }

}
