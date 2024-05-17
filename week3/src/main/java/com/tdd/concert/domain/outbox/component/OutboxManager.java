package com.tdd.concert.domain.outbox.component;

import com.tdd.concert.domain.outbox.model.Outbox;
import com.tdd.concert.domain.outbox.repository.OutboxCoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OutboxManager {

  private final OutboxCoreRepository outboxCoreRepository;

  public Outbox save(Outbox outbox) {
    return outboxCoreRepository.save(outbox);
  }

  public Outbox findOutboxById(Long id) {
    return outboxCoreRepository.findOutboxById(id);
  }

}
