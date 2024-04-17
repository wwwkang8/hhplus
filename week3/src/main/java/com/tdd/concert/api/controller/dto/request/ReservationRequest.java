package com.tdd.concert.api.controller.dto.request;

import java.time.LocalDate;

import com.tdd.concert.domain.concert.model.Concert;
import com.tdd.concert.domain.seat.model.Seat;
import com.tdd.concert.domain.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequest {

  private long userId;

  private String token;

  private long concertId;

  private LocalDate concertDate;

  private long seatNo;

  private User user;

  private Concert concert;

  private Seat seat;

  public ReservationRequest(User user, Concert concert, Seat seat) {
    this.user = user;
    this.concert = concert;
    this.seat = seat;
  }

  public ReservationRequest(long userId, String token, long concertId,
                            LocalDate concertDate) {
    this.userId = userId;
    this.token = token;
    this.concertId = concertId;
    this.concertDate = concertDate;
  }

  public ReservationRequest(long userId, String token, long concertId,
                            LocalDate concertDate, long seatNo) {
    this.userId = userId;
    this.token = token;
    this.concertId = concertId;
    this.concertDate = concertDate;
    this.seatNo = seatNo;
  }

  public ReservationRequest(long userId, String token, long concertId,
                            LocalDate concertDate, Seat seat) {
    this.userId = userId;
    this.token = token;
    this.concertId = concertId;
    this.concertDate = concertDate;
    this.seat = seat;
  }

}
