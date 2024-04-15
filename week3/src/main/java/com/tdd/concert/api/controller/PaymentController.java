package com.tdd.concert.api.controller;

import com.tdd.concert.api.controller.dto.request.PaymentRequest;
import com.tdd.concert.api.controller.dto.response.PaymentResponse;
import com.tdd.concert.api.usecase.PaymentUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentUseCase paymentUseCase;

    @PostMapping("")
    public ResponseEntity<PaymentResponse> payment(@RequestBody PaymentRequest request) {

        return ResponseEntity.ok().body(paymentUseCase.payment(request));
    }

}
