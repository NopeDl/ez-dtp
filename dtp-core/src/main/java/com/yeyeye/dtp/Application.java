package com.yeyeye.dtp;

import com.yeyeye.dtp.core.EnableDynamicThreadPool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yeyeye
 * @Date 2023/5/20 14:14
 */
@SpringBootApplication
@EnableDynamicThreadPool
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
