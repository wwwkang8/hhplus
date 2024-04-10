package com.tdd.concert.domain.token.component;

import java.time.LocalDateTime;

import com.tdd.concert.domain.token.model.Token;
import com.tdd.concert.domain.token.status.ProgressStatus;
import com.tdd.concert.domain.user.component.UserManagerImpl;
import com.tdd.concert.domain.user.model.User;
import com.tdd.concert.api.controller.dto.request.TokenRequestDto;
import com.tdd.concert.api.controller.dto.response.TokenResponseDto;
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

    Token tokenEntity = null;

    // 0. 사용자 생성
    User user = userManagerImpl.createUser();

    // 1. 토큰 발행
    String token = tokenGenerator.generateToken(user.getUserId());

    // 2. 대기 순번조회
    long waitNo = tokenGenerator.selectNextWaitNo();
    //if(waitNo == 0) waitNo++;

    // 3. 대기 상태조회
    ProgressStatus status = tokenGenerator.getCurrentQueueStatus();

    // 4. Token 엔티티 생성(현재 상태에 따라서 토큰 만료시각을 다르게 설정)
    if(status == ProgressStatus.ONGOING) { // 토큰 만료시간은 10분 후
      tokenEntity = Token.builder()
          .user(user)
          .token(token)
          .waitNo(waitNo)
          .progressStatus(status)
          .createdAt(LocalDateTime.now())
          .expiredAt(LocalDateTime.now().plusMinutes(10)) //토큰이 10분간 유효함.
          .build();
    } else if(status == ProgressStatus.WAIT) { // 대기상태이기 때문에 토큰의 만료시간을 설정하지 않음.
      tokenEntity = Token.builder()
          .user(user)
          .token(token)
          .waitNo(waitNo)
          .progressStatus(status)
          .createdAt(LocalDateTime.now()) // 대기상태이기 때문에 토큰의 만료시간을 설정하지 않음.
          .build();
    }


    // 4. 발급한 토큰 테이블에 저장
    Token savedTokenEntity = tokenGenerator.insertTokenTable(tokenEntity);

    return TokenResponseDto.from(savedTokenEntity);
  }

  @Override
  public TokenResponseDto validateToken(TokenRequestDto request) {

    TokenResponseDto tokenResponseDto = tokenValidator.validateToken(request.getToken());

    return tokenResponseDto;
  }

}
