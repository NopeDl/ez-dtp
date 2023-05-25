package com.yeyeye.dtp.common.support;

import org.springframework.beans.factory.DisposableBean;

import java.util.concurrent.*;

/**
 * @author yeyeye
 * @Date 2023/5/19 22:14
 */
public interface ExecutorAdapter<T extends Executor> extends Executor, DisposableBean {
    /**
     * 获取源线程池
     *
     * @return 线程池
     */
    T getOrigins();

    @Override
    default void execute(Runnable command) {
        getOrigins().execute(command);
    }

    void setCorePoolSize(int corePoolSize);

    int getCorePoolSize();

    void setMaximumPoolSize(int maximumPoolSize);

    int getMaximumPoolSize();

    int getLargestPoolSize();

    long getTaskNum();
    int getQueueSize();

    int getQueueRemainingCapacity();

    void setKeepAliveTime(long keepAliveTime, TimeUnit timeUnit);

    void setThreadFactory(ThreadFactory threadFactory);

    ThreadFactory getThreadFactory();

    void setRejectedExecutionHandler(RejectedExecutionHandler rejectedExecutionHandler);

    RejectedExecutionHandler getRejectedExecutionHandler();

    BlockingQueue<Runnable> getQueue();
}
