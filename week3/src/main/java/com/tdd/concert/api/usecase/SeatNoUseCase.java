package com.tdd.concert.api.usecase;

import java.time.LocalDate;
import java.util.List;

import com.tdd.concert.api.controller.dto.response.ConcertResponseDto;
import com.tdd.concert.domain.concert.component.ConcertManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SeatNoUseCase {

  private final ConcertManager concertManager;

  public ConcertResponseDto seatNoList(long concertId, LocalDate concertDate) {

    List<Long> seatNoList = concertManager.seatNoList(concertId, concertDate);

    ConcertResponseDto responseDto = new ConcertResponseDto(concertId, concertDate, seatNoList);

    return responseDto;
  }

}
