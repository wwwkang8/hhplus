package com.tdd.concert.domain.token.infra;

import com.tdd.concert.domain.token.model.Token;
import com.tdd.concert.domain.token.repository.TokenCoreRepository;
import com.tdd.concert.domain.token.repository.TokenRepository;
import com.tdd.concert.domain.token.status.ProgressStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class TokenCoreRepositoryImpl implements TokenCoreRepository {

  private final TokenRepository tokenRepository;

  @Override
  public Token findByToken(String token) {
    return tokenRepository.findTokenByToken(token);
  }

  @Override
  public Token findByWaitNo(long waitNo) {
    return tokenRepository.findTokenByWaitNo(waitNo);
  }

  @Override
  public long selectNextWaitNo() {
    return tokenRepository.selectNextWaitNo();
  }

  @Override
  public Token save(Token token) {
    return tokenRepository.save(token);
  }

  @Override
  public Long getNextPriorityWaitNo(ProgressStatus status) {
    return tokenRepository.getNextPriorityWaitNo(status);
  }

  @Override
  public Long getProgressStatusCount(ProgressStatus status) {
    return tokenRepository.getProgressStatusCount(status);
  }
}
