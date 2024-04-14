package com.tdd.concert.domain.payment.repository;

import com.tdd.concert.domain.payment.model.Payment;

public interface PaymentCoreRepository {

  public Payment save(Payment payment);

}
