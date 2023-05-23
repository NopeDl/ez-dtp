package com.yeyeye.dtp.spring;

import com.yeyeye.dtp.refresh.Refresher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.SmartLifecycle;

import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yeyeye
 * @Date 2023/5/23 0:47
 */
@Slf4j
public class DtpLifeCycle implements SmartLifecycle {
    private final AtomicBoolean isRunning = new AtomicBoolean(false);

    @Resource
    private Refresher refresher;

    @Override
    public void start() {
        if (isRunning.compareAndSet(false, true)) {
            log.info("lifecycle start");
        }
    }

    @Override
    public void stop() {
        if (isRunning.compareAndSet(true, false)) {
            log.info("lifecycle stop");
        }
    }

    @Override
    public boolean isRunning() {
        return isRunning.get();
    }
}
