package com.yeyeye.dtp.common.utils;

import com.yeyeye.dtp.common.properties.DtpProperties;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.Environment;

import java.util.Map;

import static com.yeyeye.dtp.common.constant.DtpPropertiesConstant.PROPERTIES_PREFIX;

/**
 * @author yeyeye
 * @Date 2023/5/20 14:40
 */
public class ResourceBundlerUtil {
    public static void bind(Environment environment, DtpProperties dtpProperties) {
        Binder binder = Binder.get(environment);
        ResolvableType resolvableType = ResolvableType.forClass(DtpProperties.class);
        Bindable<Object> bindable = Bindable.of(resolvableType).withExistingValue(dtpProperties);
        binder.bind(PROPERTIES_PREFIX, bindable);
    }

    public static void bind(Map<?, Object> properties, DtpProperties dtpProperties) {
        ConfigurationPropertySource sources = new MapConfigurationPropertySource(properties);
        Binder binder = new Binder(sources);
        ResolvableType type = ResolvableType.forClass(DtpProperties.class);
        Bindable<?> target = Bindable.of(type).withExistingValue(dtpProperties);
        binder.bind(PROPERTIES_PREFIX, target);
    }
}
