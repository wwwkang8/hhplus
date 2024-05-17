package com.tdd.concert.api.payment_transaction_separation.eventListener;

import com.tdd.concert.api.payment_transaction_separation.event.ChangeStatusEvent;
import com.tdd.concert.api.payment_transaction_separation.tx2.ChangeStatus_Tx2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChangeStatusEventListener {

  private final ChangeStatus_Tx2 changeStatus_tx2;

  @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
  public void handleOutboxEvent(ChangeStatusEvent event) {
    long seatId = event.getSeatId();
    long reservationId = event.getReservationId();

    changeStatus_tx2.changeStatus(seatId, reservationId);

    log.info("[ChangeStatusEventListener] ChangeStatus_Tx2 호출 완료");
  }

}
