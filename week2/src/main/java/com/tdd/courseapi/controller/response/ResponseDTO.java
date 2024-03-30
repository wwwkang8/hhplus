package com.tdd.courseapi.controller.response;

import com.tdd.courseapi.constant.ReservationStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseDTO {

  private ReservationStatus status;
  private String message;

  public ResponseDTO() {
  }

  public ResponseDTO(ReservationStatus status, String message) {
    this.status = status;
    this.message = message;
  }
}
