package com.tdd.concert.domain.token.repository;

import com.tdd.concert.domain.token.model.Token;
import com.tdd.concert.dto.request.TokenRequestDto;

public interface TokenCoreRepository {

  public Token findByToken(String token);

  public long selectNextWaitNo();

  public Token save(Token token);

}
