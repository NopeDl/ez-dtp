package com.yeyeye.dtp.core;

import com.yeyeye.dtp.support.pool.DtpExecutor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import static com.yeyeye.dtp.common.properties.DtpPropertiesConstant.CORE_POOL_SIZE;
import static com.yeyeye.dtp.common.properties.DtpPropertiesConstant.MAXIMUM_POOL_SIZE;


/**
 * @author yeyeye
 * @Date 2023/5/19 23:42
 */
@Slf4j
public class DtpRegistryTest {
    @Test
    public void testDtpExecutor() {
        DtpExecutor dtpExecutor = new DtpExecutor(1, 2, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<>(5));
        DtpRegistry.registry("dtpExecutor", dtpExecutor);
        log.info("核心数{}，最大数{}", dtpExecutor.getCorePoolSize(), dtpExecutor.getMaximumPoolSize());
        Map<String, Object> map = new HashMap<>();
        map.put(MAXIMUM_POOL_SIZE, 0);
        map.put(CORE_POOL_SIZE, 5);
        DtpRegistry.refresh("dtpExecutor", map);
        log.info("核心数{}，最大数{}", dtpExecutor.getCorePoolSize(), dtpExecutor.getMaximumPoolSize());
    }
}
