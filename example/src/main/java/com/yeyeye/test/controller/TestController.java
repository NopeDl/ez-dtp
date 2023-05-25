package com.yeyeye.test.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yeyeye
 * @Date 2023/5/20 16:57
 */
@RestController
@Slf4j
public class TestController {
    @Resource
    private Executor dtpExecutor1;

    @Resource
    private Executor dtpExecutor2;

//    @Resource
//    private Executor testPool;

    @RequestMapping("/test")
    public void test() {
        AtomicInteger a = new AtomicInteger();
        for (int i = 0; i < 100; i++) {
            dtpExecutor1.execute(() -> {
                log.info("数字：{}", a.getAndIncrement());
            });

            dtpExecutor2.execute(() -> {
                log.info("数字：{}", a.getAndIncrement());
            });
        }
    }

    @RequestMapping("/get")
    public void get() {
        log.info(dtpExecutor1.toString());
        log.info(dtpExecutor2.toString());
    }
}
