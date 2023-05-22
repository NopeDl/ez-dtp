package com.yeyeye.dtp.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

import static com.yeyeye.dtp.common.properties.DtpPropertiesConstant.PROPERTIES_PREFIX;

/**
 * @author yeyeye
 * @Date 2023/5/19 22:57
 */
@Data
@ConfigurationProperties(prefix = PROPERTIES_PREFIX)
public class DtpProperties {
    private List<ThreadPoolProperties> executors;

    private NacosProperties nacos;
}
