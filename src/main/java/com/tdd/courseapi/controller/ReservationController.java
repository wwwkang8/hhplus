package com.tdd.courseapi.controller;

import com.tdd.courseapi.constant.ReservationStatus;
import com.tdd.courseapi.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

  private final ReservationService reservationService;

  @GetMapping("{userId}")
  public ReservationStatus getReservationStatus(@PathVariable long userId) {
    return reservationService.getSuccessFail(userId);
  }

}
