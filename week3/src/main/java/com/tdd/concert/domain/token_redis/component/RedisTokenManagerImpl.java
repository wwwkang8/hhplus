package com.tdd.concert.domain.token_redis.component;

import java.util.List;

import com.tdd.concert.domain.token_redis.repository.RedisTokenCoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisTokenManagerImpl implements RedisTokenManager {

  private final RedisTokenCoreRepository redisTokenCoreRepository;
  private final RedisTokenGenerator redisTokenGenerator;

  /**
   * 대기열에서 Token의 순번을 조회
   * @param concertId 콘서트ID
   * @param token 사용자 토큰
   * @return 대기열내  순번
   * */
  @Override
  public Long findTokenRank(Long concertId, String token) {
    return redisTokenCoreRepository.findTokenRank(concertId, token);
  }

  /**
   * 대기열에 토큰 추가
   * @param concertId 콘서트ID
   * @param token 사용자 토큰
   * @return 대기열 내 순번
   * */
  @Override
  public Long addWaitingQueue(Long concertId, String token) {
    return redisTokenCoreRepository.addWaitingQueue(concertId, token);
  }

  /**
   * 대기열에서 우선순위 토큰 가져오기
   * @param concertId 콘서트ID
   * @param availableNum Working Queue에 들어갈 갯수
   * @return 토큰 리스트
   * */
  @Override
  public List<String> popTokensFromWaitingQueue(Long concertId, long availableNum) {
    return redisTokenCoreRepository.popTokensFromWaitingQueue(concertId, availableNum);
  }

  /**
   * 예약대기열에 있는 사용자 수 조회
   * @param concertId 콘서트ID
   * @return 현재 예약 중인 사용자수
   * */
  @Override
  public Long currentUsersInWorkingQueue(Long concertId) {
    return redisTokenCoreRepository.currentUsersInWorkingQueue(concertId);
  }

  /**
   * 예약열에 토큰들을 추가
   * @param concertId 콘서트ID
   * @param tokenList 대기열에서 예약열로 추가할 토큰 리스트
   * @return 예약열에 진입한 토큰 갯수
   * */
  @Override
  public int addWorkingQueue(Long concertId, List<String> tokenList) {
    return redisTokenCoreRepository.addWorkingQueue(concertId, tokenList);
  }

  /**
   * 토큰의 상태를 대기->예약으로 변경
   * @param token 대기->예약으로 상태가 변경되는 토큰
   * */
  @Override
  public void changeTokenStatus(String token) {
    redisTokenCoreRepository.changeTokenStatus(token);
  }

  @Override
  public void popFromWorkingQueue(Long concertId, String token) {
    redisTokenCoreRepository.popFromWorkingQueue(concertId, token);
  }

  @Override
  public String generateToken(Long userId) {
    return redisTokenGenerator.generateToken(userId);
  }
}
