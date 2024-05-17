package com.tdd.concert.api.payment_transaction_separation.event;

import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

@ToString
@Getter
public class PointEvent extends ApplicationEvent {

  private Long userId;
  private int price;
  private Long seatId;
  private Long reservationId;


  public PointEvent(Object source, Long userId, int price, Long seatId, Long reservationId) {
    super(source);
    this.userId = userId;
    this.price = price;
    this.seatId = seatId;
    this.reservationId = reservationId;
  }
}
