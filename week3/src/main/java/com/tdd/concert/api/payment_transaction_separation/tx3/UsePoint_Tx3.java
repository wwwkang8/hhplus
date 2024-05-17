package com.tdd.concert.api.payment_transaction_separation.tx3;

import com.tdd.concert.api.payment_transaction_separation.event.OutboxEvent;
import com.tdd.concert.api.payment_transaction_separation.event.SendDataEvent;
import com.tdd.concert.domain.user.component.UserManager;
import com.tdd.concert.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UsePoint_Tx3 {

  private final UserManager userManager;
  private final ApplicationEventPublisher applicationEventPublisher;

  public void usePoint(Long userId, int price, Long seatId, Long reservationId) {

    User user = userManager.usePoint(userId, price);

    // Outbox 테이블에 메시지 insert 한다
    OutboxEvent outboxEvent = new OutboxEvent(this, "SEND_DATA",userId, seatId, reservationId);
    applicationEventPublisher.publishEvent(outboxEvent);

    // 좌석상태변경, 예약상태변경 트랜잭션 호출
    SendDataEvent
        sendDataEvent = new SendDataEvent(this, userId,  seatId, reservationId);
    applicationEventPublisher.publishEvent(sendDataEvent);
  }


}
