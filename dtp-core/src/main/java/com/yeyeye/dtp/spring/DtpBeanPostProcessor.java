package com.yeyeye.dtp.spring;

import com.yeyeye.dtp.core.DtpRegistry;
import com.yeyeye.dtp.core.DynamicThreadPool;
import com.yeyeye.dtp.common.support.ThreadPoolExecutorAdapter;
import com.yeyeye.dtp.common.pool.DtpExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author yeyeye
 * @Date 2023/5/20 15:49
 */
@Slf4j
public class DtpBeanPostProcessor implements BeanPostProcessor, BeanFactoryAware {
    private DefaultListableBeanFactory beanFactory;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (!(bean instanceof Executor)) {
            return bean;
        }
        if (bean instanceof DtpExecutor) {
            //直接纳入管理
            DtpRegistry.registry(beanName, (DtpExecutor) bean);
        } else {
            //是一个原版线程池
            DynamicThreadPool annotationOnBean = beanFactory.findAnnotationOnBean(beanName, DynamicThreadPool.class);
            if (Objects.isNull(annotationOnBean)) {
                //没加注解，不需要管理
                return bean;
            }
            //把这个原版线程池包装一下
            if (bean instanceof ThreadPoolExecutor) {
                DtpRegistry.registry(beanName, new ThreadPoolExecutorAdapter((ThreadPoolExecutor) bean));
            }
        }
        return bean;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }
}
