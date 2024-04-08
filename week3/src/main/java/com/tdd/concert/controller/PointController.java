package com.tdd.concert.controller;

import com.tdd.concert.dto.response.PointResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/point")
@RequiredArgsConstructor
@Slf4j
public class PointController {

    @GetMapping("/{userId}")
    public ResponseEntity<PointResponseDto> getPoint(@PathVariable long userId) {
        return null;
    }

    @PostMapping("/charge")
    public ResponseEntity<PointResponseDto> chargePoint() {
        return null;
    }



}
