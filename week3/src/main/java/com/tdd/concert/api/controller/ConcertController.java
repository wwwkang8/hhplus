package com.tdd.concert.api.controller;

import com.tdd.concert.api.controller.dto.response.ConcertResponseDto;
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


    @GetMapping("/{concertId}")
    public ResponseEntity<ConcertResponseDto> getConcertList(@PathVariable long concertId) {

        return null;
    }


    @GetMapping("/{concertId}/calendar/{concertDate}")
    public ResponseEntity<ConcertResponseDto> getAvailableConcertSeat(@PathVariable long concertId,
                                                                      @PathVariable String concertDate) {

        return null;
    }


}
