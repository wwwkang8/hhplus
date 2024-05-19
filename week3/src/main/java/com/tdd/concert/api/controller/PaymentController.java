package com.tdd.concert.api.controller;

import com.tdd.concert.api.controller.dto.request.PaymentRequest;
import com.tdd.concert.api.controller.dto.response.PaymentResponse;
import com.tdd.concert.api.payment_transaction_separation.tx1.CreatePayment_Tx1;
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
    private final CreatePayment_Tx1 createPayment_tx1;

    @PostMapping("")
    public ResponseEntity<PaymentResponse> payment(@RequestBody PaymentRequest request) {

        return ResponseEntity.ok().body(paymentUseCase.payment(request));
    }

    @PostMapping("/v2")
    public ResponseEntity<PaymentResponse> paymentV2(@RequestBody PaymentRequest request) {

        return ResponseEntity.ok().body(createPayment_tx1.payment(request));
    }

}
