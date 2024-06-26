package com.tdd.concert.api.payment_transaction_separation.tx2;

import com.tdd.concert.api.payment_transaction_separation.event.OutboxEvent;
import com.tdd.concert.api.payment_transaction_separation.event.PaymentEvent;
import com.tdd.concert.api.payment_transaction_separation.event.PointEvent;
import com.tdd.concert.domain.reservation.component.ReservationReader;
import com.tdd.concert.domain.reservation.model.Reservation;
import com.tdd.concert.domain.seat.component.SeatReader;
import com.tdd.concert.domain.seat.model.Seat;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChangeStatus_Tx2 {

  private final SeatReader seatReader;
  private final ReservationReader reservationReader;
  private final ApplicationEventPublisher applicationEventPublisher;

  @Transactional
  public void changeStatus(Long seatId, Long reservationId) {
    Long userId;

    Seat seat = seatReader.findSeatBySeatIdWithExclusiveLock(seatId);
    seat.soldOut();

    Reservation reservation = reservationReader.findReservationByReservationId(reservationId);
    reservation.reserveSuccess();

    userId = reservation.getUser().getUserId();

    // Outbox 테이블에 메시지 insert 한다
    OutboxEvent outboxEvent = new OutboxEvent(this, "POINT",userId, seatId, reservationId);
    applicationEventPublisher.publishEvent(outboxEvent);


    // 좌석상태변경, 예약상태변경 트랜잭션 호출
    PointEvent pointEvent = new PointEvent(this, "NORMAL",userId,  reservation.getSeat().getPrice(), seatId, reservationId);
    applicationEventPublisher.publishEvent(pointEvent);
  }

  @Transactional
  public void changeStatusRollBack(Long seatId, Long reservationId) {
    Long userId;

    Seat seat = seatReader.findSeatBySeatIdWithExclusiveLock(seatId);
    seat.soldOutRollBack();

    Reservation reservation = reservationReader.findReservationByReservationId(reservationId);
    reservation.reserveSuccessRollBack();

    userId = reservation.getUser().getUserId();

    // Outbox 테이블에 메시지 insert 한다
    OutboxEvent outboxEvent = new OutboxEvent(this, "POINT_ROLLBACK",userId, seatId, reservationId);
    applicationEventPublisher.publishEvent(outboxEvent);


    // 좌석상태변경, 예약상태변경 트랜잭션 호출
    PaymentEvent
        paymentEvent = new PaymentEvent(this, "ROLLBACK",userId, seatId, reservationId);
    applicationEventPublisher.publishEvent(paymentEvent);
  }


}
