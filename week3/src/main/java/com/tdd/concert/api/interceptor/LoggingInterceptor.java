package com.tdd.concert.api.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoggingInterceptor implements HandlerInterceptor {

    private static final String START_TIME_ATTR = "startTime";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        long startTime = System.currentTimeMillis();  // 요청 시작 시간
        request.setAttribute(START_TIME_ATTR, startTime);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        Long startTime = (Long) request.getAttribute(START_TIME_ATTR);
        if (startTime != null) {
            long endTime = System.currentTimeMillis(); // 요청 완료 시간
            long duration = endTime - startTime;

            String method = request.getMethod();
            String uri = request.getRequestURI();
            System.out.printf("▶ [%s] %s - 처리 시간: %dms%n", method, uri, duration);
        }
    }

}
