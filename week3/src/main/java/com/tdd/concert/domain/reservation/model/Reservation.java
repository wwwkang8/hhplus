package com.tdd.concert.domain.reservation.model;

import java.time.LocalDate;

import com.tdd.concert.domain.concert.model.Concert;
import com.tdd.concert.domain.seat.model.Seat;
import com.tdd.concert.domain.user.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
@Table
@Builder
public class Reservation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="reservation_id")
  private long reservationId;

  @ManyToOne
  @JoinColumn(name="user_id")
  private User user;

  @Column(name="reservation_date")
  private LocalDate reservationDate;

  @Column(name="reservation_status")
  private ReservationStatus reservationStatus;

  @ManyToOne
  @JoinColumn(name = "concert_id")
  private Concert concert;

  @OneToOne
  @JoinColumn(name="seat_id")
  private Seat seat;

  // 엔티티 객체에 책임을 부여하기 위해서 예약 생성 메서드 추가
  public static Reservation makeReservation(User user,
                                            Concert concert,
                                            Seat seat,
                                            ReservationStatus reservationStatus) {

    return Reservation.builder()
                .user(user)
                .reservationDate(LocalDate.now())
                .reservationStatus(reservationStatus)
                .concert(concert)
                .seat(seat)
                .build();
  }
}
