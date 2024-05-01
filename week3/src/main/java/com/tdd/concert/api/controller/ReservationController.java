package com.tdd.concert.api.controller;

import com.tdd.concert.api.concurrency.ReserveSeatOptimisticLock;
import com.tdd.concert.api.controller.dto.request.ReservationRequest;
import com.tdd.concert.api.controller.dto.response.ReservationResponse;
import com.tdd.concert.api.usecase.ReserveSeatUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservation")
@RequiredArgsConstructor
@Slf4j
public class ReservationController {

    private final ReserveSeatUseCase reserveSeatUseCase;
    private final ReserveSeatOptimisticLock reserveSeatOptimisticLock;

    @PostMapping("")
    public ResponseEntity<ReservationResponse> reserveConcertSeat(@RequestBody ReservationRequest request) {
        return ResponseEntity.ok().body(reserveSeatUseCase.reserve(request));
    }

    @PostMapping("/optimistic")
    public ResponseEntity<ReservationResponse> reserveConcertSeatOptimistic(@RequestBody ReservationRequest request) {
        return ResponseEntity.ok().body(reserveSeatOptimisticLock.reserve(request));
    }

}
