package com.yeyeye.dtp.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author yeyeye
 * @Date 2023/5/20 14:26
 */
@Slf4j
public class DtpImportSelector implements DeferredImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        log.info("进入ImportSelector");
        return new String[]{
                DtpLifeCycle.class.getName(),
                DtpConfiguration.class.getName(),
                DtpImportBeanDefinitionRegistrar.class.getName(),
                DtpBeanPostProcessor.class.getName()
        };
    }
}
