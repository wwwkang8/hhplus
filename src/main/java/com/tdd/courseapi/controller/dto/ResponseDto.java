package com.tdd.courseapi.controller.dto;

import com.tdd.courseapi.constant.ReservationStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseDto {

  private ReservationStatus status;

  public ResponseDto() {
  }

  public ResponseDto(ReservationStatus status) {
    this.status = status;
  }
}
