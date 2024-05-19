package com.tdd.concert.domain.outbox.repository;

import com.tdd.concert.domain.outbox.model.Outbox;

public interface OutboxCoreRepository {

  public Outbox save(Outbox outbox);

  public Outbox findOutboxById(Long id);

}
