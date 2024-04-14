package com.tdd.concert.api.usecase;

import java.time.LocalDate;
import java.util.List;

import com.tdd.concert.api.controller.dto.response.ConcertResponse;
import com.tdd.concert.domain.concert.component.ConcertManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConcertDateUseCase {

  private final ConcertManager concertManager;


  public ConcertResponse availableConcertDate(long concertId) {

    List<LocalDate> concertDateList = concertManager.availableConcertSchedule(concertId);

    ConcertResponse concertResponse = new ConcertResponse(concertId, concertDateList);

    return concertResponse;
  }


}
