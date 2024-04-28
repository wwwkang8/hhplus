package com.tdd.concert.api.controller.dto.response;

import java.time.LocalDate;

import com.tdd.concert.api.controller.mockapi.PaymentResult;
import com.tdd.concert.domain.payment.model.Payment;
import com.tdd.concert.domain.reservation.model.Reservation;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class PaymentResponse {

  private long userId;

  private String token;

  private long concertId;

  private LocalDate concertDate;

  private long seatNo;

  private PaymentResult paymentResult;

  public PaymentResponse() {
  }

  public PaymentResponse(long userId, String token, long concertId,
                         LocalDate concertDate, long seatNo,
                         PaymentResult paymentResult) {
    this.userId = userId;
    this.token = token;
    this.concertId = concertId;
    this.concertDate = concertDate;
    this.seatNo = seatNo;
    this.paymentResult = paymentResult;
  }

  public PaymentResponse(long userId, long concertId, LocalDate concertDate, long seatNo,
                         PaymentResult paymentResult) {
    this.userId = userId;
    this.concertId = concertId;
    this.concertDate = concertDate;
    this.seatNo = seatNo;
    this.paymentResult = paymentResult;
  }

  public static PaymentResponse from(Payment payment) {

    return PaymentResponse.builder()
                          .userId(payment.getUser().getUserId())
                          .token(payment.getUser().getToken())
                          .concertId(payment.getReservation().getConcert().getConcertId())
                          .concertDate(payment.getReservation().getSeat().getConcertSchedule()
                              .getConcertDate())
                          .seatNo(payment.getReservation().getSeat().getSeatNo())
                          .paymentResult(PaymentResult.SUCCESS)
                          .build();

  }
}
