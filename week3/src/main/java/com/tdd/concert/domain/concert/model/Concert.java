package com.tdd.concert.domain.concert.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@Getter
@ToString
@Setter
public class Concert {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long concertId;

  @Column(name="name")
  private String name;

  @Column(name="singer")
  private String singer;

}
