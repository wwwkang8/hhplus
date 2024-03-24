package com.tdd.courseapi.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import com.tdd.courseapi.constant.CourseCode;
import com.tdd.courseapi.constant.ReservationStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table
public class ReservationEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long id;

  private long userId;
  private CourseCode courseCode;
  private LocalDateTime courseStartDtm;
  private LocalDate courseStartDate;
  private ReservationStatus reservationStatus;
  private LocalDateTime registeredAt;
  private LocalDateTime modifiedAt;

  public ReservationEntity(long id, long userId, CourseCode courseCode,
                           LocalDateTime courseStartDtm, LocalDate courseStartDate,
                           ReservationStatus reservationStatus, LocalDateTime registeredAt,
                           LocalDateTime modifiedAt) {
    this.id = id;
    this.userId = userId;
    this.courseCode = courseCode;
    this.courseStartDtm = courseStartDtm;
    this.courseStartDate = courseStartDate;
    this.reservationStatus = reservationStatus;
    this.registeredAt = registeredAt;
    this.modifiedAt = modifiedAt;
  }

  public ReservationEntity() {
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReservationEntity that = (ReservationEntity) o;
    return id == that.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
