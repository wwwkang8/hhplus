package com.tdd.concert.api.controller.dto.request;

import com.tdd.concert.domain.point.model.PointRscd;
import com.tdd.concert.domain.user.model.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SecondaryRow;

@Getter
@Setter
@ToString
public class PointRequest {

  private long userId;

  private int amount;

  private PointRscd pointRscd;

  private User user;

  public PointRequest() {
  }

  public PointRequest(long userId, int amount) {
    this.userId = userId;
    this.amount = amount;
  }

  public PointRequest(long userId, int amount, PointRscd pointRscd) {
    this.userId = userId;
    this.amount = amount;
    this.pointRscd = pointRscd;
  }
}
