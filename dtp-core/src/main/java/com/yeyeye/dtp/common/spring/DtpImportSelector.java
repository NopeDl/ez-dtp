package com.yeyeye.dtp.common.spring;

import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author yeyeye
 * @Date 2023/5/20 14:26
 */
public class DtpImportSelector implements DeferredImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{
                DtpConfig.class.getName(),
                DtpImportBeanDefinitionRegistrar.class.getName()
        };
    }
}
