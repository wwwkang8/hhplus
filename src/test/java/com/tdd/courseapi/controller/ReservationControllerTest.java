package com.tdd.courseapi.controller;

import com.tdd.courseapi.common.ReservationManager;
import com.tdd.courseapi.common.ReservationReader;
import com.tdd.courseapi.common.ReservationValidation;
import com.tdd.courseapi.common.ReservationWriter;
import com.tdd.courseapi.constant.ReservationStatus;
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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

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
  }

  @DisplayName("조회-특정사용자의 신청내역이 없을 때")
  @Test
  void case1() throws Exception {
    // given : 특강 신청된 데이터가 없기 때문에 FAIL을 리턴한다.
    long userId = 1L;
    ReservationStatus status = reservationService.getSuccessFail(userId);

    // when

    // then
    mvc.perform(get("/reservation/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(status.toString()));
  }

  @DisplayName("조회-특정 사용자의 신청내역이 있는 경우")
  @Test
  void case2() throws Exception {
    // given : 특정 사용자의 특강 신청내역이 있는 경우
    long userId = 2L;
    ReservationEntity reservationEntity = new ReservationEntity(1L, userId, courseEntity,LocalDate.now());
    reservationRepository.save(reservationEntity);
    ReservationStatus status = reservationService.getSuccessFail(userId);

    // when

    // then
    mvc.perform(get("/reservation/{userId}", userId))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status").value(status.toString()));
  }

  @DisplayName("예약-사용자의 특강신청")
  @Test
  void case3() throws Exception {
    // given : 사용자가 특강신청하는 경우
    long userId = 2L;

    // when
    ReservationStatus status = reservationService.reserve(userId);

    // then
    mvc.perform(get("/reservation/{userId}", userId))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status").value(status.toString()));
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
    long userId2 = 31L;

    // when
    for(int i=0; i<30; i++) {
      reservationService.reserve(userId);
      userId++;
    }

    ReservationStatus status = reservationService.reserve(userId2);

    // then
    mvc.perform(post("/reservation/{userId}", userId))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect((result)->assertTrue(result.getResolvedException().getClass().isAssignableFrom(RuntimeException.class)));
  }

  @DisplayName("예약-기등록된 사용자가 또 특강신청하는 경우")
  @Test
  void case5() throws Exception {

    // given : 사용자가 특강신청하는 경우
    long userId = 1L;
    reservationService.reserve(userId);

    // when
    ReservationStatus status = reservationService.reserve(userId);

    // then
    mvc.perform(post("/reservation/{userId}", userId))
        .andExpect(status().isInternalServerError())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.message").value("이미 강의가 신청되어있습니다."));
  }


}
