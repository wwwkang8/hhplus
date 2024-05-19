package com.tdd.concert.api.payment_transaction_separation.eventListener;

import com.tdd.concert.api.payment_transaction_separation.event.ChangeStatusEvent;
import com.tdd.concert.api.payment_transaction_separation.event.PointEvent;
import com.tdd.concert.api.payment_transaction_separation.tx3.UsePoint_Tx3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class PointEventListener {

  private final UsePoint_Tx3 usePoint_tx3;
  private final ApplicationEventPublisher applicationEventPublisher;

  @Async
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void handleOutboxEvent(PointEvent event) {

    Long userId = event.getUserId();
    int price = event.getPrice();
    Long seatId = event.getSeatId();
    Long reservationId = event.getReservationId();

    try {
      usePoint_tx3.usePoint(userId, price, seatId, reservationId);

    } catch(Exception e) {
      e.getStackTrace();
      // 보상 트랜잭션
      ChangeStatusEvent changeStatusEvent = new ChangeStatusEvent(this, "ROLLBACK",seatId, reservationId );
      applicationEventPublisher.publishEvent(changeStatusEvent);

    }

    log.info("[PointEventListener] UsePoint 트랜잭션 호출 완료");
  }

}
