package com.tdd.concert.domain.token.repository;

import java.util.List;

import com.tdd.concert.domain.token.model.Token;
import com.tdd.concert.domain.token.status.ProgressStatus;

public interface TokenCoreRepository {

  public Token findByToken(String token);

  public Token findByWaitNo(long waitNo);

  public long selectNextWaitNo();

  public Token save(Token token);

  public Long getNextPriorityWaitNo(ProgressStatus status);

  public Long getProgressStatusCount(ProgressStatus status);

  public List<Token> findExpiredTokenList(ProgressStatus status);

}
