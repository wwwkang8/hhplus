package com.tdd.concert.domain.token.component;

import java.time.LocalDateTime;

import com.tdd.concert.domain.token.model.Token;
import com.tdd.concert.domain.token.repository.TokenCoreRepository;
import com.tdd.concert.domain.token.status.ProgressStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@RequiredArgsConstructor
public class TokenScheduler {

    private final TokenCoreRepository tokenCoreRepository;

    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void updateToken() {

        long ongoingCount = tokenCoreRepository.getProgressStatusCount(ProgressStatus.ONGOING);
        long waitCount = tokenCoreRepository.getProgressStatusCount(ProgressStatus.WAIT);

        if(ongoingCount < 50) {
            int availableCount = (int) (50 - ongoingCount);

            for(int i = 0; i<Math.min(availableCount, waitCount); i++) {
                long nextWaitNo = tokenCoreRepository.getNextPriorityWaitNo(ProgressStatus.WAIT); // 가장 우선인 다음 대기순번 고객

                Token token = tokenCoreRepository.findByWaitNo(nextWaitNo);

                if(token != null) {
                    // 토큰의 속성을 변경하고 저장하는 부분
                    token.setExpiredAt(LocalDateTime.now().plusMinutes(10));
                    token.setProgressStatus(ProgressStatus.ONGOING);
                    // 업데이트된 토큰은 영속성 컨텍스트에 의해 관리됨
                    tokenCoreRepository.save(token);
                }

            }
        }
    }
}
