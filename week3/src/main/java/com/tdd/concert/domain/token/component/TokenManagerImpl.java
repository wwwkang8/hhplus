package com.tdd.concert.domain.token.component;

import com.tdd.concert.domain.token.model.Token;
import com.tdd.concert.domain.token.status.ProgressStatus;
import com.tdd.concert.domain.user.component.UserManagerImpl;
import com.tdd.concert.domain.user.model.User;
import com.tdd.concert.dto.request.TokenRequestDto;
import com.tdd.concert.dto.response.TokenResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenManagerImpl implements TokenManager{

  private final TokenGenerator tokenGenerator;
  private final TokenValidator tokenValidator;
  private final UserManagerImpl userManagerImpl;


  @Transactional
  public TokenResponseDto insertQueue(TokenRequestDto request) {

    // 0. 사용자 생성
    User user = userManagerImpl.createUser();

    // 1. 토큰 발행
    String token = tokenGenerator.generateToken(user.getUserId());

    // 2. 대기 순번조회
    long waitNo = tokenGenerator.selectLastWaitNo();
    if(waitNo == 0) waitNo++;

    // 3. 대기 상태조회
    ProgressStatus status = tokenGenerator.getCurrentQueueStatus();

    // 4. Token 엔티티 생성
    Token tokenEntity = Token.builder()
                             .user(user)
                             .token(token)
                             .waitNo(waitNo)
                             .progressStatus(status)
                             .build();


    // 4. 발급한 토큰 테이블에 저장
    Token savedTokenEntity = tokenGenerator.insertTokenTable(tokenEntity);

    return TokenResponseDto.from(savedTokenEntity);
  }

}
