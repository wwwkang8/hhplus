package com.tdd.concert.api.controller;

import java.time.LocalDate;

import com.tdd.concert.api.controller.dto.response.ConcertResponseDto;
import com.tdd.concert.api.usecase.ConcertDateUseCase;
import com.tdd.concert.api.usecase.SeatNoUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/concert")
@RequiredArgsConstructor
@Slf4j
public class ConcertController {

    private final ConcertDateUseCase concertDateUseCase;
    private final SeatNoUseCase seatNoUseCase;


    @GetMapping("/{concertId}")
    public ResponseEntity<ConcertResponseDto> getConcertList(@PathVariable long concertId) {

        log.info("[/api/concert/{concertId}] 콘서트날짜 조회 진입");

        return ResponseEntity.ok().body(concertDateUseCase.availableConcertDate(concertId));
    }


    @GetMapping("/{concertId}/calendar/{concertDate}")
    public ResponseEntity<ConcertResponseDto> getSeatNoList(@PathVariable long concertId,
                                                                      @PathVariable LocalDate concertDate) {

        return ResponseEntity.ok().body(seatNoUseCase.seatNoList(concertId, concertDate));
    }


}
