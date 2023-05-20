package com.yeyeye.dtp.common.core;

import com.yeyeye.dtp.common.spring.DtpImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yeyeye
 * @Date 2023/5/19 23:48
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(DtpImportSelector.class)
public @interface EnableDynamicThreadPool {
}
