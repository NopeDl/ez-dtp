package com.yeyeye.dtp.spring;

import com.yeyeye.dtp.core.DtpRegistry;
import com.yeyeye.dtp.support.ExecutorAdapter;
import com.yeyeye.dtp.support.pool.DtpExecutor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author yeyeye
 * @Date 2023/5/20 15:49
 */
public class DtpBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof DtpExecutor) {
            DtpRegistry.registry(beanName, (ExecutorAdapter) bean);
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
