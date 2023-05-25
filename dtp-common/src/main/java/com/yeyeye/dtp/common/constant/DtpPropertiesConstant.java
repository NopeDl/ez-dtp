package com.yeyeye.dtp.common.constant;

/**
 * @author yeyeye
 * @Date 2023/5/20 0:07
 */
public class DtpPropertiesConstant {
    /**
     * 属性前缀
     */
    public static final String PROPERTIES_PREFIX = "spring.dtp";

    /**
     * 属性前缀
     */
    public static final String THREAD_FACTORY_PREFIX = "-td-";

    /**
     * 线程池核心参数
     */
    public static final String POOL_NAME = "poolName";
    public static final String CORE_POOL_SIZE = "corePoolSize";
    public static final String MAXIMUM_POOL_SIZE = "maximumPoolSize";
    public static final String KEEP_ALIVE_TIME = "keepAliveTime";
    public static final String THREAD_FACTORY = "threadFactory";
    public static final String REJECTED_EXECUTION_HANDLER = "RejectedExecutionHandler";
}
