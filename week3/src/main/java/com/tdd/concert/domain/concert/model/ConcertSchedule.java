package com.tdd.concert.domain.concert.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Entity
@Table
@Getter
@ToString
@Setter
@AllArgsConstructor
public class ConcertSchedule {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long concertScheduleId;

  @Column(name="concert_date")
  private LocalDate concertDate;

  @ManyToOne
  @JoinColumn(name="concert_id")
  private Concert concert;

  public ConcertSchedule(LocalDate concertDate, Concert concert) {
    this.concertDate = concertDate;
    this.concert = concert;
  }
}
