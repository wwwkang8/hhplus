package com.tdd.concert.domain.concert.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@Setter
@Getter
@ToString
public class ConcertPk implements Serializable {

  private long concertId;
  private long seatNo;

  public ConcertPk() {
  }

  public ConcertPk(long concertId, long seatNo) {
    this.concertId = concertId;
    this.seatNo = seatNo;
  }
}
