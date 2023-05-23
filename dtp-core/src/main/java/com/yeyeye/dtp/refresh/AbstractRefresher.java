package com.yeyeye.dtp.refresh;


import cn.hutool.core.util.ArrayUtil;
import com.yeyeye.dtp.common.properties.DtpProperties;
import com.yeyeye.dtp.common.properties.ThreadPoolProperties;
import com.yeyeye.dtp.common.utils.ResourceBundlerUtil;
import com.yeyeye.dtp.core.DtpRegistry;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yeyeye.dtp.common.properties.DtpPropertiesConstant.CORE_POOL_SIZE;
import static com.yeyeye.dtp.common.properties.DtpPropertiesConstant.MAXIMUM_POOL_SIZE;

/**
 * @author yeyeye
 * @Date 2023/5/21 14:44
 */
@Slf4j
public class AbstractRefresher implements Refresher {
    @Resource
    protected DtpProperties dtpProperties;

    @Override
    public void refresh(String content) {
        log.info("refreshing:{}", content);
        //解析yml
        Yaml yaml = new Yaml();
        ResourceBundlerUtil.bindDtpProperties(yaml.load(content), dtpProperties);
        List<ThreadPoolProperties> executors = dtpProperties.getExecutors();
        if (!ArrayUtil.isEmpty(executors)) {
            executors.forEach((executor) -> {
                DtpRegistry.refresh(executor.getPoolName(), assembleParams(executor));
            });
        }
    }

    protected Map<String, Object> assembleParams(ThreadPoolProperties threadPoolProperties) {
        Map<String, Object> res = new HashMap<>();
        res.put(MAXIMUM_POOL_SIZE, threadPoolProperties.getMaximumPoolSize());
        res.put(CORE_POOL_SIZE, threadPoolProperties.getCorePoolSize());
        return res;
    }
}
