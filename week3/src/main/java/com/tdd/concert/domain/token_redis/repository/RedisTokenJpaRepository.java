package com.tdd.concert.domain.token_redis.repository;

import com.tdd.concert.domain.token_redis.model.RedisToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RedisTokenJpaRepository extends JpaRepository<RedisToken, Long> {

  public RedisToken findRedisTokenByRedisToken(String redisToken);

}
