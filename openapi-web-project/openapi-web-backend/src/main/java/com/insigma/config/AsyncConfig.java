package com.insigma.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {
    /*
   此处成员变量应该使用@Value从配置中读取
    */
    private int corePoolSize = 50;
    private int maxPoolSize = 100;
    private int queueCapacity = 300;
    private int keepAliveSeconds= 100;

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor poolTaskExecutor = new ThreadPoolTaskExecutor();
        //线程池所使用的缓冲队列
        poolTaskExecutor.setQueueCapacity(queueCapacity);
        //线程池维护线程的最少数量
        poolTaskExecutor.setCorePoolSize(corePoolSize);
        //线程池维护线程的最大数量
        poolTaskExecutor.setMaxPoolSize(maxPoolSize);
        //线程池维护线程所允许的空闲时间
        poolTaskExecutor.setKeepAliveSeconds(keepAliveSeconds);
        poolTaskExecutor.initialize();
        return poolTaskExecutor;
    }
}
