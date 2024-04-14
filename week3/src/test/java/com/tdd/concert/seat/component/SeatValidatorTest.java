package com.tdd.concert.seat.component;

import com.tdd.concert.domain.seat.component.SeatValidator;
import com.tdd.concert.domain.seat.model.Seat;
import com.tdd.concert.domain.seat.model.SeatStatus;
import com.tdd.concert.domain.seat.repository.SeatCoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SeatValidatorTest {

  private SeatValidator seatValidator;

  @BeforeEach
  void setUp() {
    SeatCoreRepository seatCoreRepository = mock(SeatCoreRepository.class);
    seatValidator = new SeatValidator(seatCoreRepository);
  }

  @DisplayName("임시배정이 되지 않은 좌석은 정상 통과.")
  @Test
  void case1() {
    // given
    Seat seat = new Seat();
    seat.setSeatNo(1L);
    seat.setSeatStatus(SeatStatus.AVAILABLE);

    // When
    seatValidator.validate(seat);


    // Then
  }


  @DisplayName("이미 임시배정된 좌석을 다시 임시배정하는 경우 오류발생.")
  @Test
  void case2() {
    // given
    Seat seat = new Seat();
    seat.setSeatNo(1L);
    seat.setSeatStatus(SeatStatus.TEMPORARY_RESERVED);

    // When
    RuntimeException exception = assertThrows(
        RuntimeException.class,
        () -> seatValidator.validate(seat)
    );

    // Then
    // Verify that the correct exception message is thrown
    assertEquals("이미 다른 사용자에게 임시배정된 좌석입니다. 좌석번호 : " + seat.getSeatNo() , exception.getMessage());
  }

  @DisplayName("이미 판매완료된 좌석을 다시 임시배정하는 경우 오류발생.")
  @Test
  void case3() {
    // given
    Seat seat = new Seat();
    seat.setSeatNo(1L);
    seat.setSeatStatus(SeatStatus.SOLDOUT);

    // When
    RuntimeException exception = assertThrows(
        RuntimeException.class,
        () -> seatValidator.validate(seat)
    );

    // Then
    // Verify that the correct exception message is thrown
    assertEquals("이미 판매완료된 좌석입니다. 좌석번호 : " + seat.getSeatNo() , exception.getMessage());
  }

}
