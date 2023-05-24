package com.yeyeye.dtp.spring;

import com.yeyeye.dtp.common.properties.DtpProperties;
import com.yeyeye.dtp.common.properties.ThreadPoolProperties;
import com.yeyeye.dtp.common.utils.BeanUtil;
import com.yeyeye.dtp.common.utils.ResourceBundlerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import java.util.List;
import java.util.Objects;

/**
 * @author yeyeye
 * @Date 2023/5/19 23:52
 */
@Slf4j
public class DtpImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware {
    private Environment environment;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        log.info("注册");
        //绑定资源
        DtpProperties dtpProperties = new DtpProperties();
        ResourceBundlerUtil.bind(environment, dtpProperties);
        List<ThreadPoolProperties> executors = dtpProperties.getExecutors();
        if (Objects.isNull(executors)) {
            log.info("未检测本地到配置文件线程池");
            return;
        }
        //注册beanDefinition
        executors.forEach((executorProp) -> {
            BeanUtil.registerIfAbsent(registry, executorProp);
        });
    }


    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
