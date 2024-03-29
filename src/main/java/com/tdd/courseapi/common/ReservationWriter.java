package com.tdd.courseapi.common;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.tdd.courseapi.constant.CourseCode;
import com.tdd.courseapi.domain.CourseEntity;
import com.tdd.courseapi.domain.ReservationEntity;
import com.tdd.courseapi.constant.ReservationStatus;
import com.tdd.courseapi.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationWriter {

  private final ReservationRepository reservationRepository;
  private final CourseReader courseReader;

  public void reserve(long userId, long courseId) {

    ReservationEntity result = reservationRepository.findByUserIdWithExclusiveLock(userId, courseId);

    CourseEntity course = courseReader.getCourse(courseId);

    if(result == null) {
      ReservationEntity reservationEntity = new ReservationEntity();
      long id = reservationEntity.getReservationId();
      reservationEntity.setReservationId(id);
      reservationEntity.setReservationDate(LocalDate.now());
      reservationEntity.setUserId(userId);
      reservationEntity.setCourseEntity(course);

      reservationRepository.save(reservationEntity);
    }
  }
}
