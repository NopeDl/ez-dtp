package com.yeyeye.dtp.common.pool;

import com.yeyeye.dtp.common.support.ExecutorAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author yeyeye
 * @Date 2023/5/19 22:10
 */
@Slf4j
public class DtpExecutor extends ThreadPoolExecutor implements ExecutorAdapter<ThreadPoolExecutor> {
    public DtpExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public DtpExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public DtpExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public DtpExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    public long getTaskNum() {
        return getTaskCount();
    }

    @Override
    public int getQueueSize() {
        return getQueue().size();
    }

    @Override
    public int getQueueRemainingCapacity() {
        return getQueue().remainingCapacity();
    }

    @Override
    public ThreadPoolExecutor getOrigins() {
        return this;
    }

    @Override
    public void destroy() throws Exception {
        log.info("线程池已被关闭");
        this.shutdown();
    }
}
