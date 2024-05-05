package com.tdd.concert.api.controller.dto.response;

import java.time.LocalDateTime;

import com.tdd.concert.domain.reservation.model.Reservation;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ReservationResponse {

  private long userId;

  private long concertId;

  private long seatNo;

  private LocalDateTime expiredAt;

  public ReservationResponse() {
  }

  public ReservationResponse(long userId, long concertId, long seatNo,
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

  public static ReservationResponse from(Reservation reservation) {

    return ReservationResponse.builder()
                               .concertId(reservation.getConcert().getConcertId())
                               .userId(reservation.getUser().getUserId())
                               .seatNo(reservation.getSeat().getSeatNo())
                               .expiredAt(reservation.getSeat().getTempReservedExpiredAt())
                               .build();
  }

  /** 낙관적락을 위한 ReservationResponse */
  public static ReservationResponse fromO(Long concertId, Long userId, Long seatNo, LocalDateTime expiredAt) {

    return ReservationResponse.builder()
        .concertId(concertId)
        .userId(userId)
        .seatNo(seatNo)
        .expiredAt(expiredAt)
        .build();
  }

  /** 비관적락을 위한 ReservationResponse */
  public static ReservationResponse fromP(Long concertId, Long userId, Long seatNo, LocalDateTime expiredAt) {

    return ReservationResponse.builder()
        .concertId(concertId)
        .userId(userId)
        .seatNo(seatNo)
        .expiredAt(expiredAt)
        .build();
  }
}
