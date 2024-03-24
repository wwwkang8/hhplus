package com.tdd.courseapi.service;

import java.util.List;

import com.tdd.courseapi.common.ReservationManager;
import com.tdd.courseapi.entity.ReservationEntity;
import com.tdd.courseapi.entity.ReservationStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@Transactional
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService{

  private final ReservationManager reservationManager;


  /*
   * [특강 신청 요건분석]
   * 1. 날짜 검증 : 4월 20일 00:00부터 강의 신청 가능.
   * 2. 최대인원 검증 : 30명이 넘는지 검증하기.
   * 3. 기등록 여부 조회 : 특정 userId로 이미 신청한 내역이 있는지 확인한다.
   * 4. 요청 순서 번호표 부여 : 요청 순서대로 번호를 부여한다. 부여할 때 기존에 등록되지 않은 경우에만 등록
   * 5.
   *
   * */
  @Override
  public ReservationEntity reserve(long userId) {
    return null;
  }

  @Override
  public ReservationEntity getReservation(long userId) {
    return reservationManager.getReservation(userId);
  }

  @Override
  public int getCurrentReservationCount() {
    return reservationManager.getCurrentReservationCount();
  }

  @Override
  public ReservationStatus getSuccessFail(long userId) {

    ReservationStatus status = reservationManager.getSuccessFail(userId);

    // 예약 성공여부가 조회되지 않는 경우 실패로 간주
    if(status == null) {
      status = ReservationStatus.FAIL;
    }

    return status;
  }
}
