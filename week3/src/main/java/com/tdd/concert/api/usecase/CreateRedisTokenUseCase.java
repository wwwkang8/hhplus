package com.tdd.concert.api.usecase;

import java.time.LocalDateTime;

import com.tdd.concert.api.controller.dto.request.RedisTokenRequest;
import com.tdd.concert.api.controller.dto.response.RedisTokenResponse;
import com.tdd.concert.domain.token_redis.component.RedisTokenManager;
import com.tdd.concert.domain.token_redis.status.ProgressStatusR;
import com.tdd.concert.domain.user.component.UserManager;
import com.tdd.concert.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreateRedisTokenUseCase {

  private final RedisTokenManager redisTokenManager;
  private final UserManager userManager;


  public RedisTokenResponse insertRedisQueue(RedisTokenRequest redistokenRequest) {
    RedisTokenResponse response = null;

    // 토큰이 없는 경우 => 사용자, 토큰 신규 발급
    if(redistokenRequest.getToken() == null) {

      // 사용자 생성
      User user = userManager.createUser();
      long userId = user.getUserId();


      // 토큰 생성
      String token = redisTokenManager.generateToken(userId);

      // 사용자와 RedisRequest에 토큰 세팅
      user.setToken(token);
      redistokenRequest.setToken(token);

      // 현재 working queue에 있는 인원이 몇 명인지 확인
      long currentUserCount = redisTokenManager.currentUsersInWorkingQueue(redistokenRequest.getConcertId());
      log.info("[CreateRedisTokenUseCase] 현재 WorkingQueue 내의 사용자수 : " + currentUserCount);

      // 50명 미만 => 바로 working queue 진입  / 50명 이상 => waiting queue로 진입
      if(currentUserCount < 50) {
        String workingQueueToken = redisTokenManager.addWorkingQueue(redistokenRequest.getConcertId(),
                                                                     redistokenRequest.getToken());

        // 응답 객체 생성
        response = new RedisTokenResponse(userId,
                                         redistokenRequest.getConcertId(),
                                         token,
                                         ProgressStatusR.ONGOING,
                                         LocalDateTime.now().plusMinutes(10));

      }else {

        // 해당 토큰의 대기열 내의 대기순번을 rank로 받아온다.
        long rank = redisTokenManager.addWaitingQueue(redistokenRequest.getConcertId(),
                                                      redistokenRequest.getToken());

        response = new RedisTokenResponse(userId,
                                          redistokenRequest.getConcertId(),
                                          token,
                                          rank,
                                          ProgressStatusR.WAIT,
                                          LocalDateTime.now().plusMinutes(60));
      }


      return response;
    }else {
      // 이미 토큰이 존재한다면 토큰의 대기순번, 상태를 리턴해준다.
      /**
       * @Todo : WorkingQueue에 있는 토큰을 조회하면 오류가 발생한다.
       * */
      long concertId = redistokenRequest.getConcertId();
      String token = redistokenRequest.getToken();

      long rank = redisTokenManager.findTokenRank(concertId, token);

      response = new RedisTokenResponse(concertId, token, rank, ProgressStatusR.WAIT);

      return response;
    }

  }

}
