package com.yeyeye.dtp.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 加在普通线程池上，可以包装成动态线程池
 *
 * @author yeyeye
 * @Date 2023/5/20 18:08
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DynamicThreadPool {
}
