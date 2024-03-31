package com.tdd.concert.domain.reservation.model;

import java.time.LocalDate;

import com.tdd.concert.domain.concert.model.Concert;
import com.tdd.concert.domain.user.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
@Table
public class Reservation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="reservationId")
  private long id;

  @ManyToOne
  @JoinColumn(name="user_id")
  private User user;

  @Column(name="reservation_date")
  private LocalDate reservationDate;

  @Column(name="seat_no")
  private String seatNo;

  @ManyToOne
  @JoinColumn(name="concert_id")
  private Concert concert;

  public Reservation() {
  }

  public Reservation(long id, User user, LocalDate reservationDate, String seatNo,
                     Concert concert) {
    this.id = id;
    this.user = user;
    this.reservationDate = reservationDate;
    this.seatNo = seatNo;
    this.concert = concert;
  }
}
