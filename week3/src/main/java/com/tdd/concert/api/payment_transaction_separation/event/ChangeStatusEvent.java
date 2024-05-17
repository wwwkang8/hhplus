package com.tdd.concert.api.payment_transaction_separation.event;

import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

@ToString
@Getter
public class ChangeStatusEvent extends ApplicationEvent {

  private Long seatId;
  private Long reservationId;

  public ChangeStatusEvent(Object source, Long seatId, Long reservationId) {
    super(source);
    this.seatId = seatId;
    this.reservationId = reservationId;
  }
}
