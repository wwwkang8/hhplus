package com.tdd.courseapi.service;

import java.util.List;

import com.tdd.courseapi.common.ReservationManager;
import com.tdd.courseapi.entity.ReservationEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@Transactional
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService{

  private final ReservationManager reservationManager;


  @Override
  public ReservationEntity reserve(long userId) {
    return null;
  }

  @Override
  public ReservationEntity getReservation(long userId) {
    return reservationManager.read(userId);
  }

  @Override
  public List<ReservationEntity> getReservationList(long userId) {
    return null;
  }
}
