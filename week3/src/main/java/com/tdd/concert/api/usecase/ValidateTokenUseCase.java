package com.tdd.concert.api.usecase;

import com.tdd.concert.domain.token.component.TokenManager;
import com.tdd.concert.api.controller.dto.request.TokenRequestDto;
import com.tdd.concert.api.controller.dto.response.TokenResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ValidateTokenUseCase {

    private final TokenManager tokenManager;

    public TokenResponseDto validateToken(HttpServletRequest request) {

        String token = request.getHeader("Authorization");
        TokenRequestDto tokenRequestDto = new TokenRequestDto(token);

        return tokenManager.validateToken(tokenRequestDto);
    }
}
