package com.yeyeye.dtp.spring;

import com.yeyeye.dtp.enums.ExecutorType;
import com.yeyeye.dtp.enums.QueueType;
import com.yeyeye.dtp.common.properties.ThreadPoolProperties;
import com.yeyeye.dtp.common.utils.BeanUtil;
import com.yeyeye.dtp.common.properties.DtpProperties;
import com.yeyeye.dtp.common.utils.ResourceBundlerUtil;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import java.util.concurrent.Executor;

/**
 * @author yeyeye
 * @Date 2023/5/19 23:52
 */
public class DtpImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private Environment environment;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        //绑定资源
        DtpProperties dtpProperties = new DtpProperties();
        ResourceBundlerUtil.bind(environment, dtpProperties);
        //注册beanDefinition
        dtpProperties.getExecutors().forEach((executorProp) -> {
            Class<? extends Executor> executorType = ExecutorType.getClazz(executorProp.getPoolType());
            Object[] args = assembleArgs(executorProp);
            BeanUtil.register(registry, executorProp.getPoolName(), executorType, args);
        });
    }

    private Object[] assembleArgs(ThreadPoolProperties executorProp) {
        return new Object[]{
                executorProp.getCorePoolSize(),
                executorProp.getMaximumPoolSize(),
                executorProp.getKeepAliveTime(),
                executorProp.getTimeUnit(),
                QueueType.getInstance(executorProp.getQueueType(), executorProp.getQueueSize()),
                executorProp.getThreadFactory(),
                executorProp.getRejectedExecutionHandler()
        };
    }


    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
