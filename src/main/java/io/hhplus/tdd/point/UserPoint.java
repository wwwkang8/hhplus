package io.hhplus.tdd.point;

import java.util.Objects;

public final class UserPoint {
  private final Long id;
  private final Long point;
  private final Long updateMillis;

  public UserPoint(
      Long id,
      Long point,
      Long updateMillis
  ) {
    this.id = id;
    this.point = point;
    this.updateMillis = updateMillis;
  }

  public Long id() {
    return id;
  }

  public Long point() {
    return point;
  }

  public Long updateMillis() {
    return updateMillis;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || obj.getClass() != this.getClass()) {
      return false;
    }
    var that = (UserPoint) obj;
    return Objects.equals(this.id, that.id) &&
        Objects.equals(this.point, that.point) &&
        Objects.equals(this.updateMillis, that.updateMillis);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, point, updateMillis);
  }

  @Override
  public String toString() {
    return "UserPoint[" +
        "id=" + id + ", " +
        "point=" + point + ", " +
        "updateMillis=" + updateMillis + ']';
  }
}
