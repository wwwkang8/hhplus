package io.hhplus.tdd.point;

import java.util.Objects;

public final class PointHistory {
  private final Long id;
  private final Long userId;
  private final TransactionType type;
  private final Long amount;
  private final Long timeMillis;

  public PointHistory(
      Long id,
      Long userId,
      TransactionType type,
      Long amount,
      Long timeMillis
  ) {
    this.id = id;
    this.userId = userId;
    this.type = type;
    this.amount = amount;
    this.timeMillis = timeMillis;
  }

  public Long id() {
    return id;
  }

  public Long userId() {
    return userId;
  }

  public TransactionType type() {
    return type;
  }

  public Long amount() {
    return amount;
  }

  public Long timeMillis() {
    return timeMillis;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || obj.getClass() != this.getClass()) {
      return false;
    }
    var that = (PointHistory) obj;
    return Objects.equals(this.id, that.id) &&
        Objects.equals(this.userId, that.userId) &&
        Objects.equals(this.type, that.type) &&
        Objects.equals(this.amount, that.amount) &&
        Objects.equals(this.timeMillis, that.timeMillis);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, userId, type, amount, timeMillis);
  }

  @Override
  public String toString() {
    return "PointHistory[" +
        "id=" + id + ", " +
        "userId=" + userId + ", " +
        "type=" + type + ", " +
        "amount=" + amount + ", " +
        "timeMillis=" + timeMillis + ']';
  }

}
