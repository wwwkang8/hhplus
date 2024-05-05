package com.tdd.concert.domain.seat_distribute.redisson;

import java.lang.reflect.Method;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class DistributedLockAop {

  private static final String REDISSON_LOCK_PREFIX = "LOCK:";

  private final RedissonClient redissonClient;
  private final AopTransaction aopTransaction;

  @Around("@annotation(com.tdd.concert.domain.seat_distribute.redisson.DistributedLock)")
  public Object lock(final ProceedingJoinPoint joinPoint) throws Throwable {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();
    DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);

    //String key = REDISSON_LOCK_PREFIX + CustomSpringELParser.getDynamicValue(signature.getParameterNames(), joinPoint.getArgs(), distributedLock.key());
    RLock rLock = redissonClient.getLock("seatRedissonLock");

    try{

      boolean available = rLock.tryLock(distributedLock.waitTime(), distributedLock.leaseTime(), distributedLock.timeUnit());  // (2)

      if (!available) {
        return false;
      }

      return aopTransaction.proceed(joinPoint);  // (3)
    }catch (InterruptedException e) {
      throw new InterruptedException();
    } finally {
      try{
        rLock.unlock();
      }catch(IllegalArgumentException e){
        log.info("Redisson Lock Already UnLock {} {}");
      }
    }
  }


}
