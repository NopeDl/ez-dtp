package com.yeyeye.dtp.common.utils;

import com.yeyeye.dtp.common.properties.DtpProperties;
import com.yeyeye.dtp.common.properties.DtpPropertiesConstant;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.Environment;

/**
 * @author yeyeye
 * @Date 2023/5/20 14:40
 */
public class ResourceBundlerUtil {
    public static void bind(Environment environment, DtpProperties dtpProperties) {
        Binder binder = Binder.get(environment);
        ResolvableType resolvableType = ResolvableType.forClass(DtpProperties.class);
        Bindable<Object> bindable = Bindable.of(resolvableType).withExistingValue(dtpProperties);
        binder.bind(DtpPropertiesConstant.PROPERTIES_PREFIX, bindable);
    }
}
