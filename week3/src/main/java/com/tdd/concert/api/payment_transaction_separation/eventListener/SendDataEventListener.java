package com.tdd.concert.api.payment_transaction_separation.eventListener;

import com.tdd.concert.api.payment_transaction_separation.event.PointEvent;
import com.tdd.concert.api.payment_transaction_separation.tx4.SendReservationData_Tx4;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class SendDataEventListener {

  private final SendReservationData_Tx4 sendReservationData_tx4;

  @Async
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void handleOutboxEvent(PointEvent event) {

    Long userId = event.getUserId();
    int price = event.getPrice();
    Long seatId = event.getSeatId();
    Long reservationId = event.getReservationId();

    sendReservationData_tx4.sendData(userId, price, seatId, reservationId);
    log.info("[SendDataEventListener] SendData 트랜잭션 호출 완료");
  }


}
