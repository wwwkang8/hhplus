package com.tdd.concert.api.controller.mockapi;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.tdd.concert.api.controller.dto.request.PaymentRequestDto;
import com.tdd.concert.api.controller.dto.request.PointRequestDto;
import com.tdd.concert.api.controller.dto.request.ReservationRequestDto;
import com.tdd.concert.api.controller.dto.response.ConcertResponseDto;
import com.tdd.concert.api.controller.dto.response.PaymentResponseDto;
import com.tdd.concert.api.controller.dto.response.PointResponseDto;
import com.tdd.concert.api.controller.dto.response.ReservationResponseDto;
import org.springframework.stereotype.Component;

@Component
public class MockManager {

  HashMap<Long, Long> waitList = new HashMap<>();
  HashMap<Long, List<LocalDate>> concertDateList = new HashMap<>();
  List<LocalDate> concertDates = new ArrayList<>();
  HashMap<Long, Integer> userPoint = new HashMap<>();
  long waitNo = 0;

  public void insertWaitList(long userId) {
    waitList.put(userId, ++waitNo);
  }

  public long getWaitNo(long userId) {
    return waitList.get(userId);
  }

  public ConcertResponseDto getAvailableConcert(long concertId) {

    LocalDate today = LocalDate.now();
    LocalDate nextDay;


    for(int i=0; i<10; i++) {
      today = today.plusDays(1);
      concertDates.add(today);
    }

    concertDateList.put(concertId, concertDates);

    ConcertResponseDto concertResponseDto = new ConcertResponseDto(concertId, concertDates);

    return concertResponseDto;
  }

  public ConcertResponseDto getAvailableSeat(long concertId, String concertDate) {

    long seatNo = 1L;
    List<Long> seatNoList = new ArrayList<>();

    for(int i = 0; i<50 ; i++) {
      seatNoList.add(seatNo++);
    }

    DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
    LocalDate date = LocalDate.parse(concertDate, formatter);

    ConcertResponseDto concertResponseDto = new ConcertResponseDto(concertId, date, seatNoList);

    return concertResponseDto;
  }

  public ReservationResponseDto reservation(ReservationRequestDto request) {

    LocalDateTime expiredAt = LocalDateTime.now().plusMinutes(5);

    ReservationResponseDto reservationResponseDto =
        new ReservationResponseDto(request.getUserId(),
                                   request.getConcertId(),
                                   request.getSeatNo(),
                                   expiredAt);

    return reservationResponseDto;
  }

  public PointResponseDto getUserPoint(long userId) {

    userPoint.put(userId, 15000);

    PointResponseDto pointResponseDto = new PointResponseDto(userId, userPoint.get(userId));

    return pointResponseDto;
  }

  public PointResponseDto chargePoint(PointRequestDto request) {

    int total = userPoint.get(request.getUserId()) + request.getAmount();

    userPoint.put(request.getUserId(), total);

    PointResponseDto pointResponseDto = new PointResponseDto(request.getUserId(), total);

    return pointResponseDto;
  }

  public PaymentResponseDto payment(PaymentRequestDto request) {

    PaymentResponseDto paymentResponseDto
          = new PaymentResponseDto(
              request.getUserId(),
              request.getConcertId(),
              request.getConcertDate(),
              request.getSeatNo(),
              PaymentResult.SUCCESS
      );

    return paymentResponseDto;
  }
}
