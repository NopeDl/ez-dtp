package com.yeyeye.dtp.common.properties;

import lombok.Data;

import java.util.concurrent.TimeUnit;

/**
 * @author yeyeye
 * @Date 2023/5/20 14:46
 */
@Data
public class ThreadPoolProperties {
    private String poolName;
    private String poolType = "common";

    /**
     * 以下都是核心参数
     */
    private int corePoolSize = 1;
    private int maximumPoolSize = 1;
    private long keepAliveTime;
    private TimeUnit timeUnit = TimeUnit.SECONDS;
    private String queueType = "arrayBlockingQueue";
    private int queueSize = 5;
    private String threadFactory;
    private String RejectedExecutionHandler;
}
