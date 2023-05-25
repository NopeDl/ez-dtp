package com.yeyeye.dtp.monitor;

import cn.hutool.core.thread.NamedThreadFactory;
import com.yeyeye.dtp.common.properties.DtpProperties;
import com.yeyeye.dtp.common.support.ExecutorAdapter;
import com.yeyeye.dtp.core.DtpRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.yeyeye.dtp.common.constant.MetricsConstant.MONITOR_MAIN_NAME;
import static com.yeyeye.dtp.common.constant.MetricsConstant.MONITOR_TP_NAME;

/**
 * @author yeyeye
 * @Date 2023/5/24 23:00
 */
@Slf4j
public class DtpMonitor implements ApplicationRunner {
    private static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE =
            new ScheduledThreadPoolExecutor(1, new NamedThreadFactory(MONITOR_TP_NAME, false));

    @Resource
    private DtpProperties dtpProperties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //执行定时任务
        SCHEDULED_EXECUTOR_SERVICE.scheduleWithFixedDelay(this::start,
                0,
                dtpProperties.getCollectInterval(),
                TimeUnit.SECONDS);
    }


    private void start() {
        collect();
    }

    /**
     * 采集数据
     */
    private void collect() {
        Set<String> executorNames = DtpRegistry.listAll();
        for (String executorName : executorNames) {
            ExecutorAdapter<?> executorAdapter = DtpRegistry.getExecutorAdapter(executorName);
            List<Tag> tags = new ArrayList<>();
            tags.add(Tag.of("executorName", executorName));
            Metrics.gauge("CorePoolSize", tags, executorAdapter, ExecutorAdapter::getCorePoolSize);
            Metrics.gauge("MaximumPoolSize", tags, executorAdapter, ExecutorAdapter::getMaximumPoolSize);
        }
    }
}
