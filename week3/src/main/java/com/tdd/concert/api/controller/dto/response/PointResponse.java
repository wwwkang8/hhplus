package com.tdd.concert.api.controller.dto.response;

public class PointResponse {

  private long userId;

  private int point;

  public PointResponse() {
  }

  public PointResponse(long userId, int point) {
    this.userId = userId;
    this.point = point;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public int getPoint() {
    return point;
  }

  public void setPoint(int point) {
    this.point = point;
  }
}
