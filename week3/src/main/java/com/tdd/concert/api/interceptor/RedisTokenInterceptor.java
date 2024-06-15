package com.tdd.concert.api.interceptor;

import java.util.Map;

import com.tdd.concert.domain.token_redis.component.RedisTokenExtractor;
import com.tdd.concert.domain.token_redis.component.RedisTokenValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisTokenInterceptor implements HandlerInterceptor {

  private final RedisTokenValidator redisTokenValidator;
  private final RedisTokenExtractor redisTokenExtractor;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    log.info("[RedisTokenInterceptor] 사용자가 인터셉터에 진입했습니다.");

    String redisToken = request.getHeader("Authorization");

    Map map = redisTokenExtractor.extractUserIdAndConcertIdFromToken(redisToken);
    long concertId = (long) map.get("concertId");
    System.out.println("콘서트 아이디 " + concertId);


    // 1. 토큰이 존재하는지 확인
    // 2. 토큰이 존재한다면 정말 유효한 대기열에 있는 토큰인지, Rank 확인
    boolean isExist = redisTokenValidator.findRedisTokenByConcertId(concertId, redisToken);

    if(isExist) {
      log.info("[RedisTokenInterceptor] 토큰 확인!. 드루와. 토큰 :  "+ redisToken);
    }else {
      log.info("[RedisTokenInterceptor] 토큰이 존재하지 않습니다. 접근 불가. 토큰 발급받고 다시 오세요");
    }

    return isExist;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                         ModelAndView modelAndView) throws Exception {

  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                              Object handler, Exception ex) throws Exception {

  }


}
