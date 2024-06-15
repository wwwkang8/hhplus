package com.tdd.concert.config;

import com.tdd.concert.api.interceptor.RedisTokenInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class RedisTokenInterceptorConfig implements WebMvcConfigurer {

  private final RedisTokenInterceptor redisTokenInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(redisTokenInterceptor)
        .addPathPatterns("/api/concert/**")
        .addPathPatterns("/api/payment/**")
        .addPathPatterns("/api/reservation");
  }

}
