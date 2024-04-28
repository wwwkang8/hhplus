package com.tdd.concert.api.controller.dto.request;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PaymentRequest {

  private long userId;

  private String token;

  private long concertId;

  private LocalDate concertDate;

  private long seatNo;

  public PaymentRequest() {
  }

  public PaymentRequest(long userId, String token, long concertId, LocalDate concertDate,
                        long seatNo) {
    this.userId = userId;
    this.token = token;
    this.concertId = concertId;
    this.concertDate = concertDate;
    this.seatNo = seatNo;
  }
}
