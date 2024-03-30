package com.tdd.courseapi.stub;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.tdd.courseapi.constant.CourseCode;
import com.tdd.courseapi.constant.ReservationStatus;
import com.tdd.courseapi.domain.ReservationEntity;

public class ReservationRepositoryStub {

  Map<Long, ReservationEntity> db = new HashMap<>();

  public ReservationEntity findById(Long userId) {

    ReservationEntity entity = db.get(userId);

    return entity;
  }

  public void save(long userId) {

//    ReservationEntity reservationEntity = new ReservationEntity();
//    long id = reservationEntity.getId();
//    reservationEntity.setId(id);
//    reservationEntity.setUserId(userId);
//    reservationEntity.setCourseCode(CourseCode.JAVA);
//    reservationEntity.setCourseStartDate(LocalDate.of(2024, 5, 1));
//    reservationEntity.setReservationStatus(ReservationStatus.SUCCESS);
//    reservationEntity.setRegisteredAt(LocalDateTime.now());
//    reservationEntity.setModifiedAt(LocalDateTime.now());
//    db.put(userId, reservationEntity);

  }

}
