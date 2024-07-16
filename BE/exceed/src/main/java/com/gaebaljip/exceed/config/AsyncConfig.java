package com.gaebaljip.exceed.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.gaebaljip.exceed.common.exception.member.MailSendException;

@EnableAsync
@Configuration
@Profile("!test")
public class AsyncConfig implements AsyncConfigurer {
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(16);
        executor.setMaxPoolSize(25);
        executor.setQueueCapacity(10);
        executor.setKeepAliveSeconds(60);
        executor.setRejectedExecutionHandler(
                (r, exec) -> {
                    throw MailSendException.EXECPTION;
                });
        executor.initialize();
        return executor;
    }
}
