package com.tdd.concert.domain.concert.model;

import java.time.LocalDate;

import com.tdd.concert.domain.concert.status.ReservationStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.logging.log4j.message.AsynchronouslyFormattable;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Entity
@Table
@Getter
@ToString
@Setter
public class Concert {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="concertId")
  private long id;

  @Column(name="seat_no")
  private String seatNo;

  @Column(name="concert_date")
  private LocalDate concertDate;

  @Column(name="reservation_status")
  private ReservationStatus reservationStatus;

  public Concert() {
  }

  public Concert(long id, String seatNo, LocalDate concertDate,
                 ReservationStatus reservationStatus) {
    this.id = id;
    this.seatNo = seatNo;
    this.concertDate = concertDate;
    this.reservationStatus = reservationStatus;
  }
}
