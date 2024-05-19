package com.tdd.concert.domain.outbox.repository;

import com.tdd.concert.domain.outbox.model.Outbox;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboxJpaRepository extends JpaRepository<Outbox, Long> {

  public Outbox findOutboxById(Long id);

}
