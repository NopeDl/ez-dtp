package com.yeyeye.dtp.spring;

import com.yeyeye.dtp.common.properties.DtpProperties;
import com.yeyeye.dtp.refresh.impl.NacosRefresher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yeyeye
 * @Date 2023/5/20 17:48
 */
@Configuration
@Slf4j
@EnableConfigurationProperties(DtpProperties.class)
public class DtpConfiguration {
    @Bean
    NacosRefresher nacosRefresher() {
        return new NacosRefresher();
    }
}
