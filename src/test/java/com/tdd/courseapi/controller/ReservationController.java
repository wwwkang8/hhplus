package com.tdd.courseapi.controller;

import com.tdd.courseapi.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ReservationController {

  @Autowired
  ReservationService reservationService;

}
