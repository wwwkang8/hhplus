package com.tdd.concert.api.controller;

import com.tdd.concert.api.controller.dto.response.PointResponse;
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
    public ResponseEntity<PointResponse> getPoint(@PathVariable long userId) {
        return null;
    }

    @PostMapping("/charge")
    public ResponseEntity<PointResponse> chargePoint() {
        return null;
    }



}
