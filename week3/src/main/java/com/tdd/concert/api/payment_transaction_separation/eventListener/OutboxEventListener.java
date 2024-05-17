package com.tdd.concert.api.payment_transaction_separation.eventListener;

import java.time.LocalDateTime;

import com.tdd.concert.api.payment_transaction_separation.event.OutboxEvent;
import com.tdd.concert.domain.outbox.component.OutboxManager;
import com.tdd.concert.domain.outbox.component.PayLoadGenerator;
import com.tdd.concert.domain.outbox.model.Outbox;
import com.tdd.concert.domain.outbox.model.OutboxStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class OutboxEventListener {

  private final OutboxManager outboxManager;
  private final PayLoadGenerator payLoadGenerator;

  @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
  public void handleOutboxEvent(OutboxEvent event) {
    // Outbox Table에 데이터를 저장하는 로직을 실행
    String eventType = event.getEventType();
    long userId = event.getUserId();
    long seatId = event.getSeatId();
    long reservationId = event.getReservationId();

    String payLoad = payLoadGenerator.generatePayload(userId, seatId, reservationId);

    // 저장 로직 수행
    Outbox outbox = new Outbox(eventType, OutboxStatus.NOT_PUBLISHED, payLoad, LocalDateTime.now());

    outboxManager.save(outbox);

    log.info("[OutboxEventListener] 이벤트 데이터를 Outbox 테이블에 저장완료");
  }
}
