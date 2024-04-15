package com.tdd.concert.domain.payment.infra;

import com.tdd.concert.domain.payment.model.Payment;
import com.tdd.concert.domain.payment.repository.PaymentCoreRepository;
import com.tdd.concert.domain.payment.repository.PaymentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentCoreRepositoryImpl implements PaymentCoreRepository {

  private final PaymentJpaRepository paymentJpaRepository;

  public Payment save(Payment payment) {
    return paymentJpaRepository.save(payment);
  }

}
