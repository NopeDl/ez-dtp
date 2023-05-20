package com.yeyeye.dtp.common.properties;

import lombok.Data;

import java.util.List;

/**
 * @author yeyeye
 * @Date 2023/5/19 22:57
 */
@Data
public class DtpProperties {

    private List<ThreadPoolProperties> executors;

}
