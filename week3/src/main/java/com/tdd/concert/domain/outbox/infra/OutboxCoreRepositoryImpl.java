package com.tdd.concert.domain.outbox.infra;

import com.tdd.concert.domain.outbox.model.Outbox;
import com.tdd.concert.domain.outbox.repository.OutboxCoreRepository;
import com.tdd.concert.domain.outbox.repository.OutboxJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class OutboxCoreRepositoryImpl implements OutboxCoreRepository {

  private final OutboxJpaRepository outboxJpaRepository;

  @Override
  public Outbox save(Outbox outbox) {
    return outboxJpaRepository.save(outbox);
  }

  @Override
  public Outbox findOutboxById(Long id) {
    return outboxJpaRepository.findOutboxById(id);
  }
}
