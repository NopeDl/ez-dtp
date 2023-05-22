package com.yeyeye.dtp.common.support;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author yeyeye
 * @Date 2023/5/20 18:28
 */
@Slf4j
public class ThreadPoolExecutorAdapter implements ExecutorAdapter<ThreadPoolExecutor> {
    private final ThreadPoolExecutor threadPoolExecutor;

    public ThreadPoolExecutorAdapter(ThreadPoolExecutor threadPoolExecutor) {
        this.threadPoolExecutor = threadPoolExecutor;
    }

    @Override
    public ThreadPoolExecutor getOrigins() {
        return threadPoolExecutor;
    }

    @Override
    public void setCorePoolSize(int corePoolSize) {
        threadPoolExecutor.setCorePoolSize(corePoolSize);
    }

    @Override
    public int getCorePoolSize() {
        return threadPoolExecutor.getCorePoolSize();
    }

    @Override
    public void setMaximumPoolSize(int maximumPoolSize) {
        threadPoolExecutor.setMaximumPoolSize(maximumPoolSize);
    }

    @Override
    public int getMaximumPoolSize() {
        return threadPoolExecutor.getMaximumPoolSize();
    }

    @Override
    public int getLargestPoolSize() {
        return threadPoolExecutor.getLargestPoolSize();
    }

    @Override
    public void setKeepAliveTime(long keepAliveTime, TimeUnit timeUnit) {
        threadPoolExecutor.setKeepAliveTime(keepAliveTime, timeUnit);
    }

    @Override
    public void setThreadFactory(ThreadFactory threadFactory) {
        threadPoolExecutor.setThreadFactory(threadFactory);
    }

    @Override
    public ThreadFactory getThreadFactory() {
        return threadPoolExecutor.getThreadFactory();
    }

    @Override
    public void setRejectedExecutionHandler(RejectedExecutionHandler rejectedExecutionHandler) {
        threadPoolExecutor.setRejectedExecutionHandler(rejectedExecutionHandler);
    }

    @Override
    public RejectedExecutionHandler getRejectedExecutionHandler() {
        return threadPoolExecutor.getRejectedExecutionHandler();
    }

    @Override
    public BlockingQueue<Runnable> getQueue() {
        return threadPoolExecutor.getQueue();
    }

    @Override
    public void destroy() throws Exception {
        log.info("线程池已被关闭");
        threadPoolExecutor.shutdown();
    }
}
