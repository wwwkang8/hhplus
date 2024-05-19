package com.tdd.concert.api.payment_transaction_separation.event;

import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

@ToString
@Getter
public class OutboxEvent extends ApplicationEvent {

  /**
   * 결제 내역을 가지고 상태변경, 포인트 사용을 위한 변수들이 담겨 있는 클래스
   * */
  private String eventType;
  private Long userId;
  private Long seatId;
  private Long reservationId;

  public OutboxEvent(Object source, String eventType, Long userId, Long seatId, Long reservationId) {
    super(source);
    this.eventType = eventType;
    this.userId = userId;
    this.seatId = seatId;
    this.reservationId = reservationId;
  }

}
