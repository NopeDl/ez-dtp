package com.yeyeye.dtp.refresh.impl;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.yeyeye.dtp.common.properties.DtpProperties;
import com.yeyeye.dtp.common.properties.NacosProperties;
import com.yeyeye.dtp.common.properties.ThreadPoolProperties;
import com.yeyeye.dtp.common.utils.BeanUtil;
import com.yeyeye.dtp.core.DtpThreadPoolCreator;
import com.yeyeye.dtp.refresh.AbstractRefresher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author yeyeye
 * @Date 2023/5/21 15:01
 */
@Slf4j
public class NacosRefresher extends AbstractRefresher implements Listener, InitializingBean, DisposableBean {
    private static final ThreadPoolExecutor EXECUTOR = DtpThreadPoolCreator.createNormalExecutor("Nacos-tp");

    @NacosInjected
    private ConfigService configService;

    @Override
    public void afterPropertiesSet() throws Exception {
        NacosProperties nacos = dtpProperties.getNacos();
        if (nacos != null) {
            configService.addListener(nacos.getDataId(), nacos.getGroup(), this);
            return;
        }
        log.info("Nacos未配置");
    }

    @Override
    public Executor getExecutor() {
        return EXECUTOR;
    }

    @Override
    public void receiveConfigInfo(String s) {
        List<ThreadPoolProperties> executors = dtpProperties.getExecutors();
        if (executors != null) {
            refresh(s);
            return;
        }
        log.info("配置为空");
    }

    @Override
    public void destroy() {
        EXECUTOR.shutdown();
    }
}
