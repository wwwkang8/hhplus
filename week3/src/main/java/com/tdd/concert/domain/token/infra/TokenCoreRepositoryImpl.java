package com.tdd.concert.domain.token.infra;

import com.tdd.concert.domain.token.model.Token;
import com.tdd.concert.domain.token.repository.TokenCoreRepository;
import com.tdd.concert.domain.token.repository.TokenJpaRepository;
import com.tdd.concert.domain.token.status.ProgressStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class TokenCoreRepositoryImpl implements TokenCoreRepository {

  private final TokenJpaRepository tokenJpaRepository;

  @Override
  public Token findByToken(String token) {
    return tokenJpaRepository.findTokenByToken(token);
  }

  @Override
  public Token findByWaitNo(long waitNo) {
    return tokenJpaRepository.findTokenByWaitNo(waitNo);
  }

  @Override
  public long selectNextWaitNo() {
    return tokenJpaRepository.selectNextWaitNo();
  }

  @Override
  public Token save(Token token) {
    return tokenJpaRepository.save(token);
  }

  @Override
  public Long getNextPriorityWaitNo(ProgressStatus status) {
    return tokenJpaRepository.getNextPriorityWaitNo(status);
  }

  @Override
  public Long getProgressStatusCount(ProgressStatus status) {
    return tokenJpaRepository.getProgressStatusCount(status);
  }
}
