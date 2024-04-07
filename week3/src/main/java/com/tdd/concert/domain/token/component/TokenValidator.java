package com.tdd.concert.domain.token.component;

import com.tdd.concert.domain.token.repository.TokenCoreRepository;
import com.tdd.concert.dto.response.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenValidator {

  private final TokenCoreRepository tokenCoreRepository;


  /* 토큰을 가지고 대기열 테이블에 해당 사용자 있는지 조회 */
//  public TokenResponseDto isTokenExist(String token) {
//    return tokenCoreRepository.findByToken(token);
//  }



  /*
  * 1. 헤더 상에 있는 토큰을 검증
  * 토큰에 있는 사용자 아이디와 생성시각을 분리.
  *
  * 사용자 테이블에 해당 사용자가 있는지 확인한다.
  * */


}
