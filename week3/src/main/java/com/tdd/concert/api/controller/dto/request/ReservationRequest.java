package com.tdd.concert.api.controller.dto.request;

import java.time.LocalDate;

public class ReservationRequest {

  private long userId;

  private String token;

  private long concertId;

  private LocalDate concertDate;

  private long seatNo;

  public ReservationRequest() {
  }

  public ReservationRequest(long userId, String token, long concertId,
                            LocalDate concertDate, long seatNo) {
    this.userId = userId;
    this.token = token;
    this.concertId = concertId;
    this.concertDate = concertDate;
    this.seatNo = seatNo;
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
}
