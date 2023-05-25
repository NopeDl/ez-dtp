package com.yeyeye.dtp.common.properties;

import lombok.Data;

import java.util.concurrent.TimeUnit;

import static com.yeyeye.dtp.common.constant.DtpPropertiesConstant.THREAD_FACTORY_PREFIX;

/**
 * @author yeyeye
 * @Date 2023/5/20 14:46
 */
@Data
public class ThreadPoolProperties {
    private String poolName;
    private String poolType = "common";

    /**
     * 是否为守护线程
     */
    private boolean isDaemon = false;

    /**
     * 以下都是核心参数
     */
    private int corePoolSize = 1;
    private int maximumPoolSize = 1;
    private long keepAliveTime;
    private TimeUnit timeUnit = TimeUnit.SECONDS;
    private String queueType = "arrayBlockingQueue";
    private int queueSize = 5;
    private String threadFactoryPrefix = THREAD_FACTORY_PREFIX;
    private String RejectedExecutionHandler;
}
