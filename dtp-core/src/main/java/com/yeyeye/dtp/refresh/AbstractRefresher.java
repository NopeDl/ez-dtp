package com.yeyeye.dtp.refresh;


import com.yeyeye.dtp.common.properties.DtpProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import javax.annotation.Resource;

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
        log.info(content);
    }
}
