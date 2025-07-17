package org.yjl.utils;

import cn.hutool.core.date.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * zset实现延迟消息队列
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class RedissonDelayQueue {
    private final RedissonClient redissonClient;
    private static final String QUEUE_NAME = "scm";

    public void addTaskToDelayQueue(String bizId) {
        RBlockingQueue<String> blockingQueue = redissonClient.getBlockingQueue(QUEUE_NAME);
        RDelayedQueue<String> delayedQueue = redissonClient.getDelayedQueue(blockingQueue);
        log.info(DateUtil.now(),"添加任务到延迟队列");
        delayedQueue.offer(bizId, 3, TimeUnit.SECONDS);
        delayedQueue.offer(bizId, 6, TimeUnit.SECONDS);
        delayedQueue.offer(bizId, 9, TimeUnit.SECONDS);
    }

    public String getTaskFromDelayQueue() throws InterruptedException {
        RBlockingQueue<String> blockingQueue = redissonClient.getBlockingQueue(QUEUE_NAME);
        RDelayedQueue<String> delayedQueue = redissonClient.getDelayedQueue(blockingQueue);
        //redisson的延迟队列通过将delayedQueue的元素转移到RBlockingQueue来实现的
        return blockingQueue.take();
    }
}
