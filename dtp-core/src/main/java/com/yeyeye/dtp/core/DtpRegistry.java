package com.yeyeye.dtp.core;

import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.yeyeye.dtp.common.properties.DtpPropertiesConstant;
import com.yeyeye.dtp.common.support.ExecutorAdapter;
import com.yeyeye.dtp.common.support.ExecutorWrapper;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

/**
 * 管理线程池
 *
 * @author yeyeye
 * @Date 2023/5/19 22:08
 */
@Slf4j
public class DtpRegistry {
    /**
     * 储存线程池
     */
    private static final Map<String, ExecutorWrapper> EXECUTOR_MAP = new ConcurrentHashMap<>();

    public static void registry(String executorName, ExecutorAdapter<?> executorAdapter) {
        //包装
        ExecutorWrapper executorWrapper = wrap(executorName, executorAdapter);
        //注册
        EXECUTOR_MAP.put(executorName, executorWrapper);
        log.info("注册线程池成功：{}，核心线程数：{}，最大线程数：{}，任务队列：{}",
                executorName,
                executorAdapter.getCorePoolSize(),
                executorAdapter.getMaximumPoolSize(),
                executorAdapter.getQueue());
    }

    public static Executor getExecutor(String executorName) {
        ExecutorWrapper executorWrapper = EXECUTOR_MAP.get(executorName);
        if (executorWrapper == null) {
            throw new NullPointerException("不存在此线程池,{" + executorName + "}");
        }
        return executorWrapper.getExecutor();
    }

    public static void refresh(String executorName, Map<String, Object> properties) {
        ExecutorWrapper executorWrapper = EXECUTOR_MAP.get(executorName);
        if (Objects.isNull(executorWrapper)) {
            log.info("刷新失败，不存在该线程池：{}", executorName);
            return;
        }
        ExecutorAdapter<?> executor = executorWrapper.getExecutor();
        //先处理最大线程数
        Object maximumPoolSize = properties.get(DtpPropertiesConstant.MAXIMUM_POOL_SIZE);
        if (!Objects.isNull(maximumPoolSize)) {
            try {
                ReflectUtil.invoke(executor, StrUtil.genSetter(DtpPropertiesConstant.MAXIMUM_POOL_SIZE), maximumPoolSize);
                //设置好了就移除
                properties.remove(DtpPropertiesConstant.MAXIMUM_POOL_SIZE);
            } catch (UtilException e) {
                log.info("刷新失败，线程池参数有误! {}, 错误参数：{}， 值：{}",
                        executorName,
                        DtpPropertiesConstant.MAXIMUM_POOL_SIZE,
                        maximumPoolSize);
                return;
            }
        }
        //再处理剩余参数
        for (Map.Entry<String, Object> entry : properties.entrySet()) {
            String property = entry.getKey();
            Object value = entry.getValue();
            try {
                ReflectUtil.invoke(executor, StrUtil.genSetter(property), value);
            } catch (UtilException e) {
                log.info("刷新失败，线程池参数有误! {}, 错误参数：{}， 值：{}", executorName, property, value);
            }
        }
    }

    /**
     * 封装成包装类
     *
     * @param executorName    线程池名字
     * @param executorAdapter 实现了适配器的线程池
     * @return 线程池包装
     */
    private static ExecutorWrapper wrap(String executorName, ExecutorAdapter<?> executorAdapter) {
        return ExecutorWrapper.builder()
                .executorName(executorName)
                .executor(executorAdapter)
                .build();
    }
}
