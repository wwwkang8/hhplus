package com.tdd.courseapi;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.tdd.courseapi.common.ReservationManager;
import com.tdd.courseapi.common.ReservationReader;
import com.tdd.courseapi.common.ReservationValidation;
import com.tdd.courseapi.common.ReservationWriter;
import com.tdd.courseapi.entity.ReservationEntity;
import com.tdd.courseapi.constant.ReservationStatus;
import com.tdd.courseapi.repository.ReservationRepository;
import com.tdd.courseapi.service.ReservationService;
import com.tdd.courseapi.service.ReservationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;


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

  @DisplayName("사용자 아이디로 신청여부 조회")
  @Test
  void 사용자_아이디로_신청여부_조회() {
    long userId = 123;
    when(reservationManager.getSuccessFail(userId)).thenReturn(ReservationStatus.SUCCESS);

    ReservationStatus status = reservationService.getSuccessFail(userId);

    assertEquals(ReservationStatus.SUCCESS, status);
    verify(reservationManager).getSuccessFail(userId);
  }

  @DisplayName("신청내역이 없는 경우")
  @Test
  void 사용자_신청내역_없는_경우() {
    long userId = 123;
    when(reservationManager.getSuccessFail(userId)).thenReturn(ReservationStatus.FAIL);

    ReservationStatus status = reservationService.getSuccessFail(userId);

    assertEquals(ReservationStatus.FAIL, status);
    verify(reservationManager).getSuccessFail(userId);
  }


  @DisplayName("특강 신청 성공")
  @Test
  void testReserve_SuccessfulReservation() {
    long userId = 123;
    // reserVationValidation 객체에서 validateRequest를 호출하면 True를 반환한다.
    when(reservationValidation.validateRequest(userId)).thenReturn(true);
    // reservationManager에서 reserve 메서드를 호출하면 예약성공 상태를 반환한다.
    when(reservationManager.reserve(userId)).thenReturn(ReservationStatus.SUCCESS);

    // reserve 메서드를 호
    ReservationStatus result = reservationService.reserve(userId);

    assertEquals(ReservationStatus.SUCCESS, result);
    verify(reservationValidation).validateRequest(userId); //validateRequest 메서드를 호출했는지 여부 검증
    verify(reservationManager).reserve(userId); // reserve 메서드를 호출했는지 여부 검
  }

}
