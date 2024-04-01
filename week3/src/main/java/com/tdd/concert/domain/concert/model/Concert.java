package com.tdd.concert.domain.concert.model;

import java.time.LocalDate;

import com.tdd.concert.domain.concert.status.ReservationStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
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

  @EmbeddedId
  private ConcertPk id;

  @Column(name="concert_date")
  private LocalDate concertDate;

  @Column(name="reservation_status")
  private ReservationStatus reservationStatus;

  public Concert() {
  }

  public Concert(ConcertPk id , LocalDate concertDate,
                 ReservationStatus reservationStatus) {
    this.id = id;
    this.concertDate = concertDate;
    this.reservationStatus = reservationStatus;
  }
}
