package com.tdd.courseapi;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.tdd.courseapi.common.ReservationManager;
import com.tdd.courseapi.common.ReservationReader;
import com.tdd.courseapi.common.ReservationWriter;
import com.tdd.courseapi.entity.ReservationEntity;
import com.tdd.courseapi.entity.ReservationStatus;
import com.tdd.courseapi.repository.ReservationRepository;
import com.tdd.courseapi.service.ReservationService;
import com.tdd.courseapi.service.ReservationServiceImpl;
import com.tdd.courseapi.stub.ReservationManagerStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class ReservationServiceTest {

  @InjectMocks private ReservationServiceImpl reservationService;

  @Mock private ReservationManager reservationManager;


//  @BeforeEach
//  void setUp () {
//    reservationWriter = new ReservationWriter(reservationRepository);
//    reservationReader = new ReservationReader(reservationRepository);
//    reservationManager = new ReservationManager(reservationReader, reservationWriter);
//    reservationService= new ReservationServiceImpl(reservationManager);
//  }

  @Test
  @DisplayName("사용자의 특강 신청내역 조회")
  void 사용자의_특강_신청내역_조회() {
    // given
    long userId = 1L;
    ReservationEntity expectedReservationEntity =
    new ReservationEntity(1L, 10L, "001", LocalDateTime.now(), LocalDate.now(), ReservationStatus.SUCCESS, 1L);
    when(reservationManager.getReservation(anyLong())).thenReturn(expectedReservationEntity);

    // when
    ReservationEntity actualReservationEntity = reservationService.getReservation(userId);

    // then
    assertEquals(expectedReservationEntity.getId(), actualReservationEntity.getId());
    assertEquals(expectedReservationEntity.getReservationStatus(), actualReservationEntity.getReservationStatus());
    assertEquals(expectedReservationEntity.getApplyOrder(), actualReservationEntity.getApplyOrder());
  }


}
