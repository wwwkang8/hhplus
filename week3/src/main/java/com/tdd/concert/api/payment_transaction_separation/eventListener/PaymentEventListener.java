package com.tdd.concert.api.payment_transaction_separation.eventListener;

import com.tdd.concert.api.payment_transaction_separation.event.PaymentEvent;
import com.tdd.concert.api.payment_transaction_separation.tx1.CreatePayment_Tx1;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentEventListener {

  private final CreatePayment_Tx1 createPayment_tx1;

  @Async
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void handleOutboxEvent(PaymentEvent event) {
    String eventType = event.getEventType();
    long userId = event.getUserId();
    long seatId = event.getSeatId();
    long reservationId = event.getReservationId();

    if(eventType.equals("NORMAL")) {
      // createPayment_tx1.payment(seatId, reservationId);
    }else if(eventType.equals("ROLLBACK")) {
      createPayment_tx1.paymentRollBack(userId, seatId, reservationId);
      log.info("[PaymentEventListener] CreatePayment_Tx1 롤백 호출 완료");
    }

  }


}
