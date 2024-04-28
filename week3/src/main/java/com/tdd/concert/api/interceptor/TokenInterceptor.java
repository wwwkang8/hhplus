package com.tdd.concert.api.interceptor;

import com.tdd.concert.domain.token.component.TokenManagerImpl;
import com.tdd.concert.api.controller.dto.request.TokenRequest;
import com.tdd.concert.api.controller.dto.response.TokenResponse;
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
public class TokenInterceptor implements HandlerInterceptor {

  private final TokenManagerImpl tokenManager;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    log.info("[인터셉터 진입] 사용자가 인터셉터에 진입했습니다.");

    String token = request.getHeader("Authorization");
    TokenRequest tokenRequest = new TokenRequest(token);
    TokenResponse tokenResponse = tokenManager.validateToken(tokenRequest);

    if(tokenResponse.getToken() == null) {
      log.info("[인터셉터] 토큰이 존재하지 않습니다. 접근 불가. 토큰 발급받고 다시 오세요");
      return false;
    }else {
      log.info("[인터셉터] 토큰 확인!. 드루와. 토큰 :  "+ tokenResponse.getToken());
      return true;
    }

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
