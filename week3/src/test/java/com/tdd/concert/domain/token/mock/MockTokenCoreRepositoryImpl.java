package com.tdd.concert.domain.token.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tdd.concert.domain.token.model.Token;
import com.tdd.concert.domain.token.repository.TokenCoreRepository;
import com.tdd.concert.domain.token.status.ProgressStatus;

public class MockTokenCoreRepositoryImpl implements TokenCoreRepository {

  List<Token> tokenList = new ArrayList<>();

  @Override
  public Token findByToken(String token) {
    // 발급된 토큰을 가지고 ArrayList를 모두 조회해서 맞는게 있으면 토큰 객체를 리턴한다.
    for (Token token1 : tokenList) {
      if (token1.getToken().equals(token)) {
        return token1;
      }
    }
    return null;
  }

  @Override
  public Token findByWaitNo(long waitNo) {

    // 대기순번을 가지고 ArrayList를 모두 조회해서 맞는게 있으면 토큰 객체를 리턴한다.
    for (Token token1 : tokenList) {
      if (token1.getWaitNo() == waitNo) {
        return token1;
      }
    }
    return null;
  }

  @Override
  public long selectNextWaitNo() {
    long waitNo = 0;
    // 대기순번을 채번하는 메서드이기 때문에, 현재 대기순번에서 +1을 해야한다.
    for (Token token1 : tokenList) {
      if (token1.getProgressStatus() == ProgressStatus.WAIT) {
        if(waitNo < token1.getWaitNo()) {
          waitNo = token1.getWaitNo();
        }
      }
    }
    return waitNo + 1; // 가장 마지막 대기순번에서 +1을 해야 새로운 대기순번을 추출
  }

  @Override
  public Token save(Token token) {
    tokenList.add(token);
    return token;
  }

  @Override
  public Long getNextPriorityWaitNo(ProgressStatus status) {
    long waitNo = 0;

    for (Token token1 : tokenList) {
      if (token1.getProgressStatus() == ProgressStatus.WAIT) {
          waitNo = token1.getWaitNo();
      }
    }
    return waitNo;
  }

  @Override
  public Long getProgressStatusCount(ProgressStatus status) {
    Map<ProgressStatus, Long> countMap = new HashMap<>();
    countMap.put(status, 0L);

    for (Token token1 : tokenList) {
      if(status == token1.getProgressStatus()) {
        long count = countMap.get(token1.getProgressStatus());
        countMap.put(token1.getProgressStatus(), count+1);
      }
    }

    return countMap.get(status);
  }

  @Override
  public List<Token> findExpiredTokenList(ProgressStatus status) {
    return null;
  }

}
