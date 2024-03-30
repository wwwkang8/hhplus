package com.tdd.courseapi.repository;

import com.tdd.courseapi.stub.ReservationRepositoryStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReservationRepositoryTest {

  private ReservationRepositoryStub stub;

  @BeforeEach
  void setUp() {
    stub = new ReservationRepositoryStub();
  }

  @DisplayName("조회-사용자 아이디로 신청내역 조회")
  @Test
  void case1() {
    long userId = 123L;


  }

}
