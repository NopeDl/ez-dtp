package com.yeyeye.dtp.common.utils;

import cn.hutool.core.io.resource.Resource;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;

import java.util.Map;

/**
 * @author yeyeye
 * @Date 2023/5/24 19:41
 */
public class ParseUtil {
    public static Map<Object, Object> parseYaml(String content) {
        YamlPropertiesFactoryBean factoryBean = new YamlPropertiesFactoryBean();
        factoryBean.setResources(new ByteArrayResource(content.getBytes()));
        return factoryBean.getObject();
    }
}
