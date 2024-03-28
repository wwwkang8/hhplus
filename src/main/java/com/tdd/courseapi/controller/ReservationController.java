package com.tdd.courseapi.controller;

import com.tdd.courseapi.constant.ReservationStatus;
import com.tdd.courseapi.controller.dto.ResponseDto;
import com.tdd.courseapi.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

  private final ReservationService reservationService;

  @GetMapping("{userId}")
  public ResponseDto getReservationSuccessFail(@PathVariable long userId) {

    ResponseDto responseDto = new ResponseDto();
    responseDto.setStatus(reservationService.getSuccessFail(userId));

    return responseDto;
  }

  @PostMapping("{userId}")
  public ResponseDto reserve(@PathVariable long userId) {
    ResponseDto responseDto = new ResponseDto();
    responseDto.setStatus(reservationService.reserve(userId));
    return responseDto;
  }


}
