package com.xx.vote.pool;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: xueqimiao
 * @Date: 2022/3/21 14:45
 */
@Configuration
@EnableAsync
public class ThreadPoolConfig {

    @Bean(ThreadPoolType.EMAIL_THREAD_POOL)
    public ThreadPoolTaskExecutor smsThreadPool(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        int availableProcessors = Runtime.getRuntime().availableProcessors();
        // 设置核心线程数
        executor.setCorePoolSize(availableProcessors);
        // 线程池维护线程的最大数量,只有在缓冲队列满了之后才会申请超过核心线程数的线程
        executor.setMaxPoolSize(availableProcessors * 4);
        //配置队列大小
        executor.setQueueCapacity(availableProcessors * 2);
        // 设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(60);
        // 设置默认线程名称
        executor.setThreadNamePrefix(ThreadPoolType.EMAIL_THREAD_POOL + "\t");
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        executor.initialize();
        return executor;
    }

}
