package com.tdd.courseapi.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table
public class Reservation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long id;

  private long userId;
  private String courseCode;
  private LocalDateTime courseStartDtm;
  private LocalDate courseStartDate;
  private ReservationStatus reservationStatus;
  private long applyOrder;
  private LocalDateTime registeredAt;
  private LocalDateTime modifiedAt;


}
