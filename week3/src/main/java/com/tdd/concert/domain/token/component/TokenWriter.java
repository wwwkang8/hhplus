package com.tdd.concert.domain.token.component;

import com.tdd.concert.domain.token.model.Token;
import com.tdd.concert.domain.token.repository.TokenCoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenWriter {

  private final TokenCoreRepository tokenCoreRepository;

  public Token insertTokenTable(Token token) {
    return tokenCoreRepository.save(token);
  }

}
