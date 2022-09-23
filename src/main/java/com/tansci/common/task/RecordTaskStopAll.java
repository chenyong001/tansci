package com.tansci.common.task;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledFuture;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author chenyong
 * @Date 2022/9/23 14:48
 * @Version 1.0
 */
@Slf4j
@Component
@EnableScheduling
@EnableAsync
public class RecordTaskStopAll {

  public RecordTaskStopAll() {
  }

  @Async
  @Scheduled(cron = "0 0 1 * * ?")
  public void delTasks() {
    log.info("==========停止全部任务,time={}", System.currentTimeMillis());
    RecordTask.scheduledFutures.keySet().forEach((taskId) -> {
      ((ScheduledFuture) RecordTask.scheduledFutures.get(taskId)).cancel(false);
      RecordTask.scheduledFutures.remove(taskId);
    });
  }
}
