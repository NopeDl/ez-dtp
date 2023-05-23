package com.yeyeye.dtp.spring;

import cn.hutool.core.util.ArrayUtil;
import com.yeyeye.dtp.common.properties.DtpProperties;
import com.yeyeye.dtp.common.properties.ThreadPoolProperties;
import com.yeyeye.dtp.core.DtpRegistry;
import com.yeyeye.dtp.refresh.Refresher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.SmartLifecycle;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author yeyeye
 * @Date 2023/5/23 0:47
 */
@Slf4j
public class DtpLifeCycle implements SmartLifecycle {
    private final AtomicBoolean isRunning = new AtomicBoolean(false);
    @Resource
    private DtpProperties dtpProperties;

    @Override
    public void start() {
        if (isRunning.compareAndSet(false, true)) {
            log.info("lifecycle start");
            //同步远端配置
            List<ThreadPoolProperties> executors = dtpProperties.getExecutors();
            if (!ArrayUtil.isEmpty(executors)) {
                for (ThreadPoolProperties executor : executors) {
                    DtpRegistry.refresh(executor.getPoolName(), executor);
                }
            }
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
