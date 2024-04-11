package com.tdd.concert.api.controller.mockapi;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.tdd.concert.api.controller.dto.request.PaymentRequest;
import com.tdd.concert.api.controller.dto.request.PointRequest;
import com.tdd.concert.api.controller.dto.request.ReservationRequest;
import com.tdd.concert.api.controller.dto.response.ConcertResponse;
import com.tdd.concert.api.controller.dto.response.PaymentResponse;
import com.tdd.concert.api.controller.dto.response.PointResponse;
import com.tdd.concert.api.controller.dto.response.ReservationResponse;
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

  public ConcertResponse getAvailableConcert(long concertId) {

    LocalDate today = LocalDate.now();
    LocalDate nextDay;


    for(int i=0; i<10; i++) {
      today = today.plusDays(1);
      concertDates.add(today);
    }

    concertDateList.put(concertId, concertDates);

    ConcertResponse concertResponse = new ConcertResponse(concertId, concertDates);

    return concertResponse;
  }

  public ConcertResponse getAvailableSeat(long concertId, String concertDate) {

    long seatNo = 1L;
    List<Long> seatNoList = new ArrayList<>();

    for(int i = 0; i<50 ; i++) {
      seatNoList.add(seatNo++);
    }

    DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
    LocalDate date = LocalDate.parse(concertDate, formatter);

    ConcertResponse concertResponse = new ConcertResponse(concertId, date, seatNoList);

    return concertResponse;
  }

  public ReservationResponse reservation(ReservationRequest request) {

    LocalDateTime expiredAt = LocalDateTime.now().plusMinutes(5);

    ReservationResponse reservationResponse =
        new ReservationResponse(request.getUserId(),
                                   request.getConcertId(),
                                   request.getSeatNo(),
                                   expiredAt);

    return reservationResponse;
  }

  public PointResponse getUserPoint(long userId) {

    userPoint.put(userId, 15000);

    PointResponse pointResponse = new PointResponse(userId, userPoint.get(userId));

    return pointResponse;
  }

  public PointResponse chargePoint(PointRequest request) {

    int total = userPoint.get(request.getUserId()) + request.getAmount();

    userPoint.put(request.getUserId(), total);

    PointResponse pointResponse = new PointResponse(request.getUserId(), total);

    return pointResponse;
  }

  public PaymentResponse payment(PaymentRequest request) {

    PaymentResponse paymentResponse
          = new PaymentResponse(
              request.getUserId(),
              request.getConcertId(),
              request.getConcertDate(),
              request.getSeatNo(),
              PaymentResult.SUCCESS
      );

    return paymentResponse;
  }
}
