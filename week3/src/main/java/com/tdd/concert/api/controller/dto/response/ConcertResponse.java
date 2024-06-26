package com.tdd.concert.api.controller.dto.response;

import java.time.LocalDate;
import java.util.List;

public class ConcertResponse {

  private long concertId;

  private List<LocalDate> concertDates;

  private LocalDate concertDate;

  private List<Long> seatNoList;

  public ConcertResponse() {
  }

  public ConcertResponse(long concertId, List<LocalDate> concertDates) {
    this.concertId = concertId;
    this.concertDates = concertDates;
  }

  public ConcertResponse(long concertId, List<LocalDate> concertDates,
                         List<Long> seatNoList) {
    this.concertId = concertId;
    this.concertDates = concertDates;
    this.seatNoList = seatNoList;
  }

  public ConcertResponse(long concertId, LocalDate concertDate,
                         List<Long> seatNoList) {
    this.concertId = concertId;
    this.concertDate = concertDate;
    this.seatNoList = seatNoList;
  }

  public ConcertResponse(long concertId, List<LocalDate> concertDates,
                         LocalDate concertDate, List<Long> seatNoList) {
    this.concertId = concertId;
    this.concertDates = concertDates;
    this.concertDate = concertDate;
    this.seatNoList = seatNoList;
  }

  public long getConcertId() {
    return concertId;
  }

  public void setConcertId(long concertId) {
    this.concertId = concertId;
  }

  public List<LocalDate> getConcertDates() {
    return concertDates;
  }

  public void setConcertDates(List<LocalDate> concertDates) {
    this.concertDates = concertDates;
  }

  public LocalDate getConcertDate() {
    return concertDate;
  }

  public void setConcertDate(LocalDate concertDate) {
    this.concertDate = concertDate;
  }

  public List<Long> getSeatNoList() {
    return seatNoList;
  }

  public void setSeatNoList(List<Long> seatNoList) {
    this.seatNoList = seatNoList;
  }

}
