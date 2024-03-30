package com.tdd.courseapi.domain;

import java.time.LocalDate;
import java.util.Objects;

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
@Setter
@Getter
@ToString
@Table
public class CourseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="course_id")
  private long courseId;

  @Column(name="course_start_date")
  private LocalDate courseStartDate;

  @Column(name="quantity")
  private int quantity;

  public CourseEntity() {
  }

  public CourseEntity(long courseId, LocalDate courseStartDate, int quantity) {
    this.courseId = courseId;
    this.courseStartDate = courseStartDate;
    this.quantity = quantity;
  }


}
