package com.tdd.courseapi.domain;

import java.time.LocalDate;
import java.util.Objects;

import com.tdd.courseapi.constant.CourseCode;
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

@Entity
@Getter
@Setter
@ToString
@Table
public class ReservationEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long reservationId;

  @Column(name = "user_id")
  private long userId;

  @ManyToOne
  @JoinColumn(name = "course_id")
  private CourseEntity courseEntity;

  @Column(name = "reservation_date")
  private LocalDate reservationDate;

  public ReservationEntity() {
  }

  public ReservationEntity(long reservationId, long userId,
                           CourseEntity courseEntity, LocalDate reservationDate) {
    this.reservationId = reservationId;
    this.userId = userId;
    this.courseEntity = courseEntity;
    this.reservationDate = reservationDate;
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
    return reservationId == that.reservationId;
  }

  @Override
  public int hashCode() {
    return Objects.hash(reservationId);
  }
}
