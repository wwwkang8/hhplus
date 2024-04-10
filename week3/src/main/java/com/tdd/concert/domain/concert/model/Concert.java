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

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long concertId;

  @Column(name="concert_date")
  private LocalDate concertDate;

  @Column(name="concert_name")
  private String concertName;

  @Column(name = "price")
  private long price;

}
