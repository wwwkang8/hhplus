package com.tdd.courseapi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import com.tdd.courseapi.common.ReservationManager;
import com.tdd.courseapi.common.ReservationValidation;
import com.tdd.courseapi.constant.ReservationStatus;
import com.tdd.courseapi.controller.request.RequestDTO;
import com.tdd.courseapi.controller.response.ResponseDTO;
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
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @DisplayName("조회-사용자 아이디로 신청여부 조회")
  @Test
  void case1() {
    long userId = 123;
    long courseId = 1L;
    when(reservationManager.getSuccessFail(userId, courseId)).thenReturn(ReservationStatus.SUCCESS);

    ReservationStatus status = reservationService.getSuccessFail(userId, courseId);

    assertEquals(ReservationStatus.SUCCESS, status);
    verify(reservationManager).getSuccessFail(userId, courseId);
  }

  @DisplayName("조회-신청내역이 없는 경우")
  @Test
  void case2() {
    long userId = 123;
    long courseId = 1L;
    when(reservationManager.getSuccessFail(userId, courseId)).thenReturn(ReservationStatus.FAIL);

    ReservationStatus status = reservationService.getSuccessFail(userId, courseId);

    assertEquals(ReservationStatus.FAIL, status);
    verify(reservationManager).getSuccessFail(userId, courseId);
  }


  @DisplayName("조회-특강 신청여부 조회")
  @Test
  void case3() {
    long userId = 123;
    long courseId = 1L;
    // reserVationValidation 객체에서 validateRequest를 호출하면 True를 반환한다.
    when(reservationValidation.validateRequest(userId, courseId)).thenReturn(true);

    // reservationManager에서 reserve 메서드를 호출하면 예약성공 상태를 반환한다.
    when(reservationManager.reserve(userId, courseId)).thenReturn(ReservationStatus.SUCCESS);

    // reserve 메서드를 호출
    RequestDTO requestDTO = new RequestDTO(userId, courseId);
    ResponseDTO responseDTO = reservationService.reserve(requestDTO);

    assertEquals(ReservationStatus.SUCCESS, responseDTO.getStatus());
    verify(reservationValidation)
        .validateRequest(userId, courseId); //validateRequest 메서드를 호출했는지 여부 검증
    verify(reservationManager).reserve(userId, courseId); // reserve 메서드를 호출했는지 여부 검증
  }

  @DisplayName("조회 - 총 신청건수 조회")
  @Test
  void case4() {
    // given
    int max = 30;
    long courseId = 1L;
    when(reservationManager.countByCourseEntityCourseId(courseId)).thenReturn(max);

    // when
    int count = reservationService.countReservationByCourseId(courseId);

    // then
    assertEquals(max, count);
  }

  @DisplayName("등록 - 특강 신청 성공")
  @Test
  void case5() {
    //given
    long userId = 123L;
    long courseId = 1L;
    RequestDTO requestDTO = new RequestDTO(userId, courseId);
    when(reservationValidation.validateRequest(userId, courseId)).thenReturn(true);
    when(reservationManager.reserve(userId, courseId)).thenReturn(ReservationStatus.SUCCESS);

    // when
    ResponseDTO responseDTO = reservationService.reserve(requestDTO);

    // then
    assertEquals(ReservationStatus.SUCCESS, responseDTO.getStatus());
  }

  @DisplayName("등록 - 특강 신청 실패")
  @Test
  void case6() {
    //given
    long userId = 123L;
    long courseId = 1L;
    RequestDTO requestDTO = new RequestDTO(userId, courseId);
    when(reservationValidation.validateRequest(userId, courseId)).thenReturn(true);
    when(reservationManager.reserve(userId, courseId)).thenReturn(ReservationStatus.FAIL);

    // when
    ResponseDTO responseDTO = reservationService.reserve(requestDTO);

    // then
    assertEquals(ReservationStatus.FAIL, responseDTO.getStatus());
  }

}
