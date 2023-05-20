package com.yeyeye.dtp.enums;

import cn.hutool.core.util.ReflectUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author yeyeye
 * @Date 2023/5/20 15:14
 */
public enum QueueType {
    COMMON("arrayBlockingQueue", ArrayBlockingQueue.class);
    private final String type;
    private final Class<?> clazz;

    private static final Map<String, Class<?>> TYPE_MAPPING = new HashMap<>();

    static {
        for (QueueType value : QueueType.values()) {
            TYPE_MAPPING.put(value.type, value.clazz);
        }
    }

    QueueType(String type, Class<?> clazz) {
        this.type = type;
        this.clazz = clazz;
    }

    public static Object getInstance(String type, int size) {
        return ReflectUtil.newInstance(TYPE_MAPPING.get(type), size);
    }

}
