package com.yhxjs.magicpacket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class ThreadExecutorConfig {

    /**
     * 核心线程数
     */
    private static final int CORE_POOL_SIZE = 10;

    /**
     * 最大线程数
     */
    private static final int MAX_POOL_SIZE = 200;

    /**
     * 保持时间
     */
    private static final int KEEP_ALIVE_TIME = 60;

    /**
     * 队列数
     */
    private static final int QUEUE_CAPACITY = 100;

    @Bean
    public ExecutorService defaultExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setKeepAliveSeconds(KEEP_ALIVE_TIME);
        executor.setQueueCapacity(QUEUE_CAPACITY);
        executor.setThreadNamePrefix("default");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor.getThreadPoolExecutor();
    }
}
