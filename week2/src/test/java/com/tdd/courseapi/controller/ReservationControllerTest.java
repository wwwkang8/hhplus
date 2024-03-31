package com.tdd.courseapi.controller;

import com.tdd.courseapi.common.ReservationManager;
import com.tdd.courseapi.common.ReservationReader;
import com.tdd.courseapi.common.ReservationValidation;
import com.tdd.courseapi.common.ReservationWriter;
import com.tdd.courseapi.constant.ReservationStatus;
import com.tdd.courseapi.controller.request.RequestDTO;
import com.tdd.courseapi.controller.response.ResponseDTO;
import com.tdd.courseapi.domain.CourseEntity;
import com.tdd.courseapi.domain.ReservationEntity;
import com.tdd.courseapi.repository.CourseRepository;
import com.tdd.courseapi.repository.ReservationRepository;
import com.tdd.courseapi.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
@AutoConfigureMockMvc
public class ReservationControllerTest {

  @Autowired
  ReservationService reservationService;

  @Autowired
  ReservationManager reservationManager;

  @Autowired
  ReservationReader reservationReader;

  @Autowired
  ReservationWriter reservationWriter;

  @Autowired
  ReservationRepository reservationRepository;

  @Autowired
  CourseRepository courseRepository;

  @Autowired
  ReservationValidation reservationValidation;

  @Autowired
  MockMvc mvc;

  private CourseEntity courseEntity;

  @BeforeEach
  void setUp() {
    courseEntity = new CourseEntity(1L, LocalDate.now(), 30);
    courseRepository.save(courseEntity);
    reservationRepository.deleteAll();
  }

  @DisplayName("조회-특정사용자의 신청내역이 없을 때")
  @Test
  void case1() throws Exception {
    // given : 특강 신청된 데이터가 없기 때문에 FAIL을 리턴한다.
    long userId = 1L;
    long courseId = 1L;
    ReservationStatus status = reservationService.getSuccessFail(userId, courseId);

    // when

    // then
    mvc.perform(get("/reservation/{userId}/{courseId}", userId, courseId))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status").value(status.toString()));
  }

  @DisplayName("조회-특정 사용자의 신청내역이 있는 경우")
  @Test
  void case2() throws Exception {
    // given : 특정 사용자의 특강 신청내역이 있는 경우
    long userId = 2L;
    long courseId = 1L;
    ReservationEntity reservationEntity =
        new ReservationEntity(1L, userId, courseEntity, LocalDate.now());
    reservationRepository.save(reservationEntity);
    ReservationStatus status = reservationService.getSuccessFail(userId, courseId);

    // when

    // then
    mvc.perform(get("/reservation/{userId}/{courseId}", userId, courseId))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status").value(status.toString()));
  }

  @DisplayName("예약-사용자의 특강신청")
  @Test
  void case3() throws Exception {
    // given : 사용자가 특강신청하는 경우
    long userId = 2L;
    long courseId = 1L;
    RequestDTO requestDTO = new RequestDTO(userId, courseId);
    String requestBody =
        String.format("{\"userId\": \"%s\", \"courseId\": \"%s\"}", userId, courseId);


    // when
    ResponseDTO responseDTO = reservationService.reserve(requestDTO);

    // then
    mvc.perform(post("/reservation/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status").value(responseDTO.getStatus().toString()));
  }

  @DisplayName("예약-30명이 이미 신청 완료된 경우")
  @Test
  void case4() throws Exception {

    /**
     * 테스트 고민 : RuntimeException을 Service 로직에서 발생시키면
     * 애초에 사용자 입장에서는 특강신청 성공인지 실패인지도 확인이 어렵게 된다.
     * 그렇다면 RuntimeException을 발생시키지 않는게 맞나?
     * */

    // given : 사용자가 특강신청하는 경우
    long userId = 1L;
    long courseId = 1L;
    long userId2 = 31L;

    // when
    for (int i = 0; i < 30; i++) {
      RequestDTO requestDTO = new RequestDTO(userId, courseId);
      reservationService.reserve(requestDTO);
      userId++;
    }

    RequestDTO requestDTO = new RequestDTO(userId2, courseId);
    ResponseDTO responseDTO = reservationService.reserve(requestDTO);

    // then
    mvc.perform(post("/reservation/{userId}", userId))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect((result) -> assertTrue(
            result.getResolvedException().getClass().isAssignableFrom(RuntimeException.class)));
  }

  @DisplayName("예약-기등록된 사용자가 또 특강신청하는 경우")
  @Test
  void case5() throws Exception {

    // given : 사용자가 특강신청하는 경우
    long userId = 1L;
    long courseId = 1L;
    RequestDTO requestDTO = new RequestDTO(userId, courseId);
    reservationService.reserve(requestDTO);

    // when
    ResponseDTO responseDTO = reservationService.reserve(requestDTO);

    // then
    mvc.perform(post("/reservation/{userId}", userId))
        .andExpect(status().isInternalServerError())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.message").value("이미 강의가 신청되어있습니다."));
  }

  @DisplayName("등록 - 동시성 테스트")
  @Test
  void case6() throws InterruptedException {
    // given : 사용자가 특강신청하는 경우
    long userId = 1L;
    long courseId = 1L;
    RequestDTO requestDTO = new RequestDTO(userId, courseId);

    final int threadCount = 100;
    final ExecutorService executorService = Executors.newFixedThreadPool(32);
    final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

    //when
    for (int i = 0; i < threadCount; i++) {
      userId += 1;
      requestDTO.setUserId(userId);
      executorService.submit(() -> {
        try {
          reservationService.reserve(requestDTO);
        } finally {
          countDownLatch.countDown();
        }
      });
    }
    countDownLatch.await();

    //then
    int count = reservationManager.countByCourseEntityCourseId(courseId);

    assertEquals(30, count);

  }


}
