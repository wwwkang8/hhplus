package com.tdd.courseapi.service;

import com.tdd.courseapi.common.ReservationManager;
import com.tdd.courseapi.common.ReservationValidation;
import com.tdd.courseapi.domain.ReservationEntity;
import com.tdd.courseapi.constant.ReservationStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@Transactional
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService{

  private final ReservationManager reservationManager;
  private final ReservationValidation reservationValidation;


  @Override
  public ReservationStatus reserve(long userId) {
    // 예약 유효성 검증선행
    boolean result = reservationValidation.validateRequest(userId);

    if(result) {
      return reservationManager.reserve(userId);
    } else {
      return ReservationStatus.FAIL;
    }
  }


  @Override
  public int getCurrentReservationCount() {
    return reservationManager.getCurrentReservationCount();
  }

  @Override
  public ReservationStatus getSuccessFail(long userId) {
    ReservationStatus status = reservationManager.getSuccessFail(userId);
    return status;
  }
}
