package com.tdd.concert.api.controller.dto.request;

public class PointRequestDto {

  private long userId;

  private int amount;

  public PointRequestDto() {
  }

  public PointRequestDto(long userId, int amount) {
    this.userId = userId;
    this.amount = amount;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }
}
