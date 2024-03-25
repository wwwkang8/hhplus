package com.tdd.courseapi.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import com.tdd.courseapi.common.ReservationWriter;
import com.tdd.courseapi.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;

public class ReservationWriterTest {

  @Mock
  private ReservationRepository reservationRepository;

  @InjectMocks
  private ReservationWriter reservationWriter;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @DisplayName("동시에 특강신청 요청이 몰리는 경우")
  @Test
  void 동시에_특강신청_ㄱㄱ () {

    ExecutorService executor = Executors.newFixedThreadPool(30);

    Runnable runnableTask = () -> {

      for(int i=0; i< 50; i++) {
        reservationWriter.reserve(i);
      }
    };

    executor.execute(runnableTask);
  }

}
