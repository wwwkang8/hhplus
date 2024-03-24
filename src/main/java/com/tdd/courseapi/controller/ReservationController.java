package com.tdd.courseapi.controller;

import com.tdd.courseapi.entity.ReservationEntity;
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
  public ReservationEntity getReservation(@PathVariable long userId) {
    return reservationService.getReservation(userId);
  }

}
