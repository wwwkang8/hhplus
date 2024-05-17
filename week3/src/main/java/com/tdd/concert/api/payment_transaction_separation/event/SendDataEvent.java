package com.tdd.concert.api.payment_transaction_separation.event;

import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

@ToString
@Getter
public class SendDataEvent extends ApplicationEvent {

  private Long userId;
  private Long seatId;
  private Long reservationId;


  public SendDataEvent(Object source, Long userId, Long seatId, Long reservationId) {
    super(source);
    this.userId = userId;
    this.seatId = seatId;
    this.reservationId = reservationId;
  }
}
