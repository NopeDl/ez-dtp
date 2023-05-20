package com.yeyeye.dtp.support;

import lombok.Builder;
import lombok.Data;

/**
 * @author yeyeye
 * @Date 2023/5/19 22:40
 */
@Data
@Builder
public class ExecutorWrapper {
    /**
     * 线程池唯一名字
     */
    private String executorName;
    private ExecutorAdapter<?> executor;
}
