package com.yeyeye.dtp.refresh;


import cn.hutool.core.util.ArrayUtil;
import com.yeyeye.dtp.common.properties.DtpProperties;
import com.yeyeye.dtp.common.properties.ThreadPoolProperties;
import com.yeyeye.dtp.common.utils.ParseUtil;
import com.yeyeye.dtp.common.utils.ResourceBundlerUtil;
import com.yeyeye.dtp.core.DtpRegistry;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.List;

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
        //解析yml
        ResourceBundlerUtil.bind(ParseUtil.parseYaml(content), dtpProperties);
          List<ThreadPoolProperties> executors = dtpProperties.getExecutors();
        if (!ArrayUtil.isEmpty(executors)) {
            executors.forEach((executor) -> DtpRegistry.refresh(executor.getPoolName(), executor));
        }
    }
}
