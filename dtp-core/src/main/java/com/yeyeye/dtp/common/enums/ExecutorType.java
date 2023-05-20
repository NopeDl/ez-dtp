package com.yeyeye.dtp.common.enums;

import com.yeyeye.dtp.common.support.pool.DtpExecutor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

/**
 * @author yeyeye
 * @Date 2023/5/20 14:56
 */
public enum ExecutorType {
    COMMON("common", DtpExecutor.class);
    private final String type;
    private final Class<? extends Executor> clazz;

    private static final Map<String, Class<? extends Executor>> TYPE_MAPPING = new HashMap<>();

    static {
        for (ExecutorType value : ExecutorType.values()) {
            TYPE_MAPPING.put(value.type, value.clazz);
        }
    }

    ExecutorType(String type, Class<? extends Executor> clazz) {
        this.type = type;
        this.clazz = clazz;
    }

    public static Class<? extends Executor> getClazz(String type) {
        return TYPE_MAPPING.get(type);
    }
}
