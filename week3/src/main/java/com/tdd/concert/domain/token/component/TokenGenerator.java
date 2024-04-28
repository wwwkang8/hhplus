package com.tdd.concert.domain.token.component;

import java.util.UUID;

import com.tdd.concert.domain.token.model.Token;
import com.tdd.concert.domain.token.repository.TokenCoreRepository;
import com.tdd.concert.domain.token.status.ProgressStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenGenerator {

  // 생성자 주입
  private final TokenCoreRepository tokenCoreRepository;

  public Token insertTokenTable(Token token) {
    return tokenCoreRepository.save(token);
  }

  public String generateToken(long userId) {

    /** 사용자ID로 토큰 생성 */
    String combinedData = userId + "";
    String token = UUID.nameUUIDFromBytes(combinedData.getBytes()).toString();

    log.info("[토큰 생성] UUID 토큰 : " + token + ", 사용자ID : " + userId);

    return token;
  }
}
