package com.tdd.concert.dto.response;

import java.time.LocalDateTime;

public class ReservationResponseDto {

  private long userId;

  private long concertId;

  private long seatNo;

  private LocalDateTime expiredAt;

  public ReservationResponseDto() {
  }

  public ReservationResponseDto(long userId, long concertId, long seatNo,
                                LocalDateTime expiredAt) {
    this.userId = userId;
    this.concertId = concertId;
    this.seatNo = seatNo;
    this.expiredAt = expiredAt;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public long getConcertId() {
    return concertId;
  }

  public void setConcertId(long concertId) {
    this.concertId = concertId;
  }

  public long getSeatNo() {
    return seatNo;
  }

  public void setSeatNo(long seatNo) {
    this.seatNo = seatNo;
  }

  public LocalDateTime getExpiredAt() {
    return expiredAt;
  }

  public void setExpiredAt(LocalDateTime expiredAt) {
    this.expiredAt = expiredAt;
  }
}
