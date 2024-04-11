package com.tdd.concert.api.controller;

import com.tdd.concert.api.controller.dto.response.ReservationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservation")
@RequiredArgsConstructor
@Slf4j
public class ReservationController {


    @PostMapping("")
    public ResponseEntity<ReservationResponse> reserveConcertSeat() {

        return null;
    }

}
