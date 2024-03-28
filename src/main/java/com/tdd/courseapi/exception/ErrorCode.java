package com.tdd.courseapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorCode {

  private EnumServerCode codeEnum;

  public ErrorCode() {
  }

  public ErrorCode(EnumServerCode codeEnum) {
    this.codeEnum = codeEnum;
  }

  public static enum EnumServerCode {
    INTERNAL_SERVER_ERROR(HttpStatus.BAD_REQUEST, "서버 오류 발생");

    EnumServerCode(HttpStatus status, String msg) {
      this.status = status;
      this.msg = msg;
    }

    private HttpStatus status;
    private String msg;

    public String getMsg() {
      return msg;
    }

    public HttpStatus getHttpStatus() {
      return status;
    }
  }
}
