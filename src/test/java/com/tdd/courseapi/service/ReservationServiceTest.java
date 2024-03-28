package com.tdd.courseapi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.tdd.courseapi.common.ReservationManager;
import com.tdd.courseapi.common.ReservationValidation;
import com.tdd.courseapi.constant.ReservationStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class ReservationServiceTest {

    @Mock
    private ReservationManager reservationManager;

    @Mock
    private ReservationValidation reservationValidation;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @BeforeEach
    void setUp () {
      MockitoAnnotations.openMocks(this);
    }

    @DisplayName("조회-사용자 아이디로 신청여부 조회")
    @Test
    void case1() {
      long userId = 123;
      when(reservationManager.getSuccessFail(userId)).thenReturn(ReservationStatus.SUCCESS);

      ReservationStatus status = reservationService.getSuccessFail(userId);

      assertEquals(ReservationStatus.SUCCESS, status);
      verify(reservationManager).getSuccessFail(userId);
    }

    @DisplayName("조회-신청내역이 없는 경우")
    @Test
    void case2() {
      long userId = 123;
      when(reservationManager.getSuccessFail(userId)).thenReturn(ReservationStatus.FAIL);

      ReservationStatus status = reservationService.getSuccessFail(userId);

      assertEquals(ReservationStatus.FAIL, status);
      verify(reservationManager).getSuccessFail(userId);
    }


    @DisplayName("조회-특강 신청여부 조회")
    @Test
    void case3() {
      long userId = 123;
      // reserVationValidation 객체에서 validateRequest를 호출하면 True를 반환한다.
      when(reservationValidation.validateRequest(userId)).thenReturn(true);

      // reservationManager에서 reserve 메서드를 호출하면 예약성공 상태를 반환한다.
      when(reservationManager.reserve(userId)).thenReturn(ReservationStatus.SUCCESS);

      // reserve 메서드를 호출
      ReservationStatus result = reservationService.reserve(userId);

      assertEquals(ReservationStatus.SUCCESS, result);
      verify(reservationValidation).validateRequest(userId); //validateRequest 메서드를 호출했는지 여부 검증
      verify(reservationManager).reserve(userId); // reserve 메서드를 호출했는지 여부 검증
    }

    @DisplayName("조회 - 총 신청건수 조회")
    @Test
    void case4() {
      // given
      int max = 30;
      when(reservationManager.getCurrentReservationCount()).thenReturn(max);

      // when
      int count = reservationService.getCurrentReservationCount();

      // then
      assertEquals(max, count);
    }

    @DisplayName("등록 - 특강 신청 성공")
    @Test
    void case5() {
      //given
      long userId = 123L;
      when(reservationValidation.validateRequest(userId)).thenReturn(true);
      when(reservationManager.reserve(userId)).thenReturn(ReservationStatus.SUCCESS);

      // when
      ReservationStatus status = reservationService.reserve(userId);

      // then
      assertEquals(ReservationStatus.SUCCESS, status);
    }

    @DisplayName("등록 - 특강 신청 실패")
    @Test
    void case6() {
      //given
      long userId = 123L;
      when(reservationValidation.validateRequest(userId)).thenReturn(true);
      when(reservationManager.reserve(userId)).thenReturn(ReservationStatus.FAIL);

      // when
      ReservationStatus status = reservationService.reserve(userId);

      // then
      assertEquals(ReservationStatus.FAIL, status);
    }

}
