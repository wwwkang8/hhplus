package com.tdd.concert.domain.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="user_id")
  private long userId;

  @Column(name="token")
  private String token;

  @Column(name="point")
  private int point;

  public User() {
  }

  public User(long userId, int point) {
    this.userId = userId;
    this.point = point;
  }

  public User(long userId, String token, int point) {
    this.userId = userId;
    this.token = token;
    this.point = point;
  }

  public long getUserId() {
    return userId;
  }

  public String getToken() {
    return token;
  }

  public int getPoint() {
    return point;
  }
}
