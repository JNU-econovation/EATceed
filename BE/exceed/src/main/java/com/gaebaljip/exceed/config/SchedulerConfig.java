package com.gaebaljip.exceed.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableScheduling
public class SchedulerConfig {
    private static final int POOL_SIZE = 2;

    @Bean("schedulerTask")
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
        executor.setPoolSize(POOL_SIZE);
        executor.setThreadNamePrefix("scheduler-thread-");
        executor.initialize();
        return executor;
    }
}
