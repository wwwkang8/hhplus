package com.tdd.concert.dto.response;

import java.time.LocalDate;

import com.tdd.concert.controller.mockapi.PaymentResult;

public class PaymentResponseDto {

  private long userId;

  private String token;

  private long concertId;

  private LocalDate concertDate;

  private long seatNo;

  private PaymentResult paymentResult;

  public PaymentResponseDto() {
  }

  public PaymentResponseDto(long userId, String token, long concertId,
                            LocalDate concertDate, long seatNo,
                            PaymentResult paymentResult) {
    this.userId = userId;
    this.token = token;
    this.concertId = concertId;
    this.concertDate = concertDate;
    this.seatNo = seatNo;
    this.paymentResult = paymentResult;
  }

  public PaymentResponseDto(long userId, long concertId, LocalDate concertDate, long seatNo,
                            PaymentResult paymentResult) {
    this.userId = userId;
    this.concertId = concertId;
    this.concertDate = concertDate;
    this.seatNo = seatNo;
    this.paymentResult = paymentResult;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public long getConcertId() {
    return concertId;
  }

  public void setConcertId(long concertId) {
    this.concertId = concertId;
  }

  public LocalDate getConcertDate() {
    return concertDate;
  }

  public void setConcertDate(LocalDate concertDate) {
    this.concertDate = concertDate;
  }

  public long getSeatNo() {
    return seatNo;
  }

  public void setSeatNo(long seatNo) {
    this.seatNo = seatNo;
  }

  public PaymentResult getPaymentResult() {
    return paymentResult;
  }

  public void setPaymentResult(PaymentResult paymentResult) {
    this.paymentResult = paymentResult;
  }
}
