package com.tdd.courseapi.common;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.tdd.courseapi.common.ReservationManager;
import com.tdd.courseapi.common.ReservationValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

public class ReservationValidationTest {

  @Mock
  ReservationManager reservationManager;

  @InjectMocks
  ReservationValidation reservationValidation;

  @BeforeEach
  void setUp () {
    // 모킹된 객체를 초기화
    MockitoAnnotations.openMocks(this);
  }

//  @DisplayName("특강신청 시각이 아닌 경우")
//  @Test
//  void 특강신청_시각이_아닌경우() {
//
//    long userId = 111;
//    when(LocalDateTime.now()).thenReturn(LocalDateTime.of(2024, 4, 3, 13, 00));
//
//    boolean status = reservationValidation.validateRequest(userId);
//
//    assertEquals(false, status);
//  }

  @DisplayName("특강신청인원이 30명이 완료된 경우")
  @Test
  void 특강신청인원_30명_완료() {
    long userId = 123;
    when(reservationManager.getCurrentReservationCount()).thenReturn(30);

    boolean result = reservationValidation.validateRequest(userId);

    assertEquals(false, result);
    //verify(reservationManager).getCurrentReservationCount();
  }

}
