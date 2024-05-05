package com.tdd.concert.api.controller.dto.request;

import java.time.LocalDate;

import com.tdd.concert.domain.concert.model.Concert;
import com.tdd.concert.domain.seat.model.Seat;
import com.tdd.concert.domain.seat_distribute.model.SeatD;
import com.tdd.concert.domain.seat_optimistic.model.SeatO;
import com.tdd.concert.domain.seat_pessimistic.model.SeatP;
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

  private long seatId;

  private String token;

  private long concertId;

  private LocalDate concertDate;

  private long seatNo;

  private User user;

  private Concert concert;

  private Seat seat;

  private SeatP seatP;

  private SeatO seatO;

  private SeatD seatD;

  public ReservationRequest(User user, Concert concert, Seat seat) {
    this.user = user;
    this.concert = concert;
    this.seat = seat;
  }

  public ReservationRequest(User user, Concert concert, SeatP seatP) {
    this.user = user;
    this.concert = concert;
    this.seatP = seatP;
  }

  public ReservationRequest(User user, Concert concert, SeatO seatO) {
    this.user = user;
    this.concert = concert;
    this.seatO = seatO;
  }

  public ReservationRequest(User user, Concert concert, SeatD seatD) {
    this.user = user;
    this.concert = concert;
    this.seatD = seatD;
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

  public ReservationRequest(long userId, String token, long concertId,
                            LocalDate concertDate, SeatO seatO) {
    this.userId = userId;
    this.token = token;
    this.concertId = concertId;
    this.concertDate = concertDate;
    this.seatO = seatO;
  }

  public ReservationRequest(long userId, String token, long concertId,
                            LocalDate concertDate,
                            SeatP seatP) {
    this.userId = userId;
    this.token = token;
    this.concertId = concertId;
    this.concertDate = concertDate;
    this.seatP = seatP;
  }

  public ReservationRequest(long userId, String token, long concertId,
                            LocalDate concertDate,
                            SeatD seatD) {
    this.userId = userId;
    this.token = token;
    this.concertId = concertId;
    this.concertDate = concertDate;
    this.seatD = seatD;
  }
}
