package com.tdd.courseapi.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SecondaryRow;

@Getter
@Setter
@ToString
public class RequestDTO {

    private long userId;
    private long courseId;

  public RequestDTO(long userId, long courseId) {
    this.userId = userId;
    this.courseId = courseId;
  }

  public RequestDTO() {
  }
}
