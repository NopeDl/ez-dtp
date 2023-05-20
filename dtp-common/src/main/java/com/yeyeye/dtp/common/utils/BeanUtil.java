package com.yeyeye.dtp.common.utils;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

/**
 * @author yeyeye
 * @Date 2023/5/20 15:09
 */
public class BeanUtil {
    public static void register(BeanDefinitionRegistry registry, String beanName, Class<?> clazz, Object[] args) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
        for (Object arg : args) {
            builder.addConstructorArgValue(arg);
        }
        registry.registerBeanDefinition(beanName, builder.getBeanDefinition());
    }
}
