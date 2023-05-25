package com.yeyeye.test.config;

import com.yeyeye.dtp.core.DynamicThreadPool;
import com.yeyeye.dtp.common.pool.DtpExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yeyeye
 * @Date 2023/5/20 17:18
 */
@Configuration
public class TestConfiguration {
    @Bean
    public DtpExecutor dtpExecutor3() {
        return new DtpExecutor(3,
                3,
                100,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100));
    }

    @Bean
    @DynamicThreadPool
    public ThreadPoolExecutor dtpExecutor44() {
        return new ThreadPoolExecutor(3,
                3,
                100,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100));
    }
}
