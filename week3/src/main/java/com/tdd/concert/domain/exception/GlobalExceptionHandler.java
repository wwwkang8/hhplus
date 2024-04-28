package com.tdd.concert.domain.exception;

import com.tdd.concert.api.controller.dto.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ExceptionResponse> handleRuntimeException(RuntimeException ex) {
    // 런타임 에러에 대한 응답생성
    ExceptionResponse response = new ExceptionResponse(ex.getMessage());
    ex.printStackTrace();
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }

}
