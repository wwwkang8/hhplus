package com.tdd.courseapi;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.tdd.courseapi.common.ReservationManager;
import com.tdd.courseapi.entity.ReservationEntity;
import com.tdd.courseapi.constant.ReservationStatus;
import com.tdd.courseapi.service.ReservationServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class ReservationServiceTest {

  @InjectMocks private ReservationServiceImpl reservationService;

  @Mock private ReservationManager reservationManager;



  @Test
  @DisplayName("사용자의 특강 신청내역 조회")
  void 사용자의_특강_신청내역_조회() {
    // given
    long userId = 1L;

  }


}
