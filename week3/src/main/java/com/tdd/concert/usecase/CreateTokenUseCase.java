package com.tdd.concert.usecase;

import com.tdd.concert.domain.token.component.TokenManagerImpl;
import com.tdd.concert.domain.token.model.Token;
import com.tdd.concert.dto.request.TokenRequestDto;
import com.tdd.concert.dto.response.TokenResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class CreateTokenUseCase {

  private final TokenManagerImpl tokenManagerImpl;

  public CreateTokenUseCase(TokenManagerImpl tokenManagerImpl) {
    this.tokenManagerImpl = tokenManagerImpl;
  }

  public TokenResponseDto insertQueue(HttpServletRequest request) {

    String token = request.getHeader("Authorization");
    TokenRequestDto tokenRequestDto = new TokenRequestDto(token);

    if(tokenRequestDto.getToken() == null) {
      return tokenManagerImpl.insertQueue(tokenRequestDto);
    }else {
      //return tokenManagerImpl.insertQueue(request);
    }

    return null;
  }


}
