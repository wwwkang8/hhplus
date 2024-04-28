package com.tdd.concert.domain.payment.component;

import com.tdd.concert.domain.payment.model.Payment;
import com.tdd.concert.domain.payment.repository.PaymentCoreRepository;
import com.tdd.concert.domain.payment.repository.PaymentJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentManager {

  private final PaymentCoreRepository paymentCoreRepository;

  public Payment payment(Payment payment) {
    return paymentCoreRepository.save(payment);
  }

}
