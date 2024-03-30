package com.tdd.courseapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
    ErrorCode errorCode = new ErrorCode(ErrorCode.EnumServerCode.INTERNAL_SERVER_ERROR);

    return handleExceptionInternal(errorCode);
  }

  private ResponseEntity<ErrorResponse> handleExceptionInternal(ErrorCode errorCode) {
    return ResponseEntity
        .status(errorCode.getCodeEnum().getHttpStatus())
        .body(makeErrorResponse(errorCode));
  }

  private ErrorResponse makeErrorResponse(ErrorCode errorCode) {
    return ErrorResponse.builder()
        .code(errorCode.getCodeEnum().name())
        .msg(errorCode.getCodeEnum().getMsg())
        .build();
  }

}
