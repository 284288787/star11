package com.star.truffle.core.async;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThreadPoolConfiguration {

  @Value("${hn.pool.thread-num:20}")
  private int threadNum;

  @Value("${hn.pool.queue-size:0}")
  private int queueSize;

  @Bean(name = "fixedThreadPool")
  public ExecutorService fixedThreadPool() {
    if (queueSize == 0) {
      return Executors.newFixedThreadPool(threadNum);
    } else {
      return new ThreadPoolExecutor(threadNum, threadNum,
          0L, TimeUnit.MILLISECONDS,
          new LinkedBlockingQueue<>(queueSize));
    }
  }

  @Bean
  public ThreadPoolHelper threadPoolHelper(
      @Autowired @Qualifier("hnThreadPool") ExecutorService executorService) {
    return new ThreadPoolHelper(executorService);
  }

}
