package com.tdd.concert.config;

import com.tdd.concert.api.interceptor.TokenInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class TokenInterceptorConfig implements WebMvcConfigurer {

  private final TokenInterceptor tokenInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(tokenInterceptor)
              //.addPathPatterns("/api/concert/*")
              .addPathPatterns("/api/payment");
              //.addPathPatterns("api/reservation");
  }
}
