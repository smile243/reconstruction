/**
 * *****************************************************
 * Copyright (C) 2019 founder.com. All Rights Reserved
 * This file is part of founder  project.
 * Unauthorized copy of this file, via any medium is strictly prohibited.
 * Proprietary and Confidential.
 * ****************************************************
 * <p>
 * History:
 * <author>            <time>          <version>          <desc>
 * croyson      2019/4/19          1.0.0
 */
package org.yjl.config;

import com.alibaba.ttl.threadpool.TtlExecutors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Future使用的线程池
 */
@Configuration
@Slf4j
public class FutureExecutorConfig {


    @Value("${run.executor.corePoolSize}")
    private Integer corePoolSize;

    @Value("${run.executor.maxPoolSize}")
    private Integer maxPoolSize;

    @Value("${run.executor.queueCapacity}")
    private Integer queueCapacity;

    @Value("${run.executor.keepAliveSeconds}")
    private Integer keepAliveSeconds;

    @Bean(value = "futureExecutor")
    public Executor getFutureExecutor() {
        int cpuNums = Runtime.getRuntime().availableProcessors();
        log.info("CPU数量:{}", cpuNums);
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setThreadNamePrefix("futureExecutor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return TtlExecutors.getTtlExecutor(executor);
    }
}
