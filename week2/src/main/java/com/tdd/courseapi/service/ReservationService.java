package com.tdd.courseapi.service;

import com.tdd.courseapi.controller.request.RequestDTO;
import com.tdd.courseapi.controller.response.ResponseDTO;
import com.tdd.courseapi.domain.ReservationEntity;
import com.tdd.courseapi.constant.ReservationStatus;

public interface ReservationService {

  public ResponseDTO reserve(RequestDTO requestDTO);

  public int countReservationByCourseId(long courseId);

  public ReservationStatus getSuccessFail(long userId, long courseId);

}
