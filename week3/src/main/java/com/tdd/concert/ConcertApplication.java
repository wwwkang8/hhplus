package com.tdd.concert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ConcertApplication {

  public static void main(String[] args) {
    SpringApplication.run(ConcertApplication.class, args);
  }

}
