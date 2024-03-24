package com.tdd.courseapi.common;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.tdd.courseapi.constant.CourseCode;
import com.tdd.courseapi.entity.ReservationEntity;
import com.tdd.courseapi.constant.ReservationStatus;
import com.tdd.courseapi.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationWriter {

  private final ReservationRepository reservationRepository;

  public void reserve(long userId) {

    long applyOrder = 0;

    ReservationEntity reservationEntity = new ReservationEntity();
    long id = reservationEntity.getId();
    reservationEntity.setId(id);
    reservationEntity.setUserId(userId);
    reservationEntity.setCourseCode(CourseCode.JAVA);
    reservationEntity.setCourseStartDate(LocalDate.of(2024, 5, 1));
    reservationEntity.setReservationStatus(ReservationStatus.SUCCESS);
    reservationEntity.setRegisteredAt(LocalDateTime.now());
    reservationEntity.setModifiedAt(LocalDateTime.now());

    reservationRepository.save(reservationEntity);


  }
}
