package com.tdd.concert.controller.mockapi;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class MockManager {

  HashMap<Long, Long> waitList = new HashMap<>();
  long waitNo = 0;

  public void insertWaitList(long userId) {
    waitList.put(userId, ++waitNo);
  }

  public long getWaitNo(long userId) {
    return waitList.get(userId);
  }

}
