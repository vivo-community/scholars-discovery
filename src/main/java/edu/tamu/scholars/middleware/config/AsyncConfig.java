package edu.tamu.scholars.middleware.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@EnableAsync
@Configuration
public class AsyncConfig implements AsyncConfigurer {

    @Bean(name = "taskScheduler")
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(16);
        scheduler.setThreadNamePrefix("thread-pool-task-scheduler");
        return scheduler;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }

    @Bean
    protected ConcurrentTaskExecutor taskExecutor() {
        return new ConcurrentTaskExecutor(this.getAsyncExecutor());
    }

}
