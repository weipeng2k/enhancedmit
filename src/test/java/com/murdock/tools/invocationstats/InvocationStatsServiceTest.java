/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.murdock.tools.invocationstats;

import java.lang.reflect.Method;
import java.util.Random;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.murdock.tools.invocationstats.component.InvocationStatsComponent;

/**
 * @author weipeng 2012-11-2 上午11:42:23
 */
@ContextConfiguration(locations = { "classpath:invocationStatsService.xml" })
public class InvocationStatsServiceTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private InvocationStatsService   invocationStatsService;

    @Autowired
    private InvocationStatsComponent invocationStatsComponent;

    @Test
    public void create() {
        TestCase.assertEquals(invocationStatsService != null, true);
    }

    @Test
    public void fetchInvocationStats() {
        System.out.println(invocationStatsComponent.addInvokeTimes(InvocationStatsComponent.class.getMethods()[0], 1));
        System.out.println(invocationStatsComponent.addExceptionTimes(InvocationStatsComponent.class.getMethods()[0], 2));
        System.out.println(invocationStatsComponent.addFailedTimes(InvocationStatsComponent.class.getMethods()[0], 3));
        System.out.println(invocationStatsComponent.addSuccessTimes(InvocationStatsComponent.class.getMethods()[0], 4));
        System.out.println(invocationStatsComponent.addSpendMillis(InvocationStatsComponent.class.getMethods()[0], 5));

        System.out.println(invocationStatsService.fetchInvocationStats(InvocationStatsComponent.class.getMethods()[0]));
    }

    @Test
    public void crontab() {
        for (int i = 0; i < InvocationStatsComponent.class.getMethods().length; i++) {
            CreatorJob cj = new CreatorJob();
            cj.method = InvocationStatsComponent.class.getMethods()[i];
            Thread thread = new Thread(cj);
            thread.start();
        }

        invocationStatsService.crontabExecuteAllStatsMethods(new PrintExecutor(), 10);
        invocationStatsService.crontabExecuteAllStatsMethods(new PrintExecutor(), 10);

        try {
            Thread.sleep(60 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    class CreatorJob implements Runnable {

        Method method;

        Random random = new Random();

        @Override
        public void run() {
            while (true) {
                invocationStatsComponent.addInvokeTimes(method, random.nextInt(100));
                invocationStatsComponent.addSuccessTimes(method, random.nextInt(10));
                invocationStatsComponent.addFailedTimes(method, random.nextInt(10));
                invocationStatsComponent.addExceptionTimes(method, random.nextInt(5));
                invocationStatsComponent.addSpendMillis(method, random.nextInt(1000));

                try {
                    Thread.sleep(random.nextInt(10));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    class PrintExecutor implements InvocationStatsExecutor {

        @Override
        public void execute(InvocationStatsInfo invocationStatsInfo) {
            System.out.println("方法名：" + invocationStatsInfo.getMethodName() + ", 调用了："
                               + invocationStatsInfo.getInvocationTimes() + ", 成功了："
                               + invocationStatsInfo.getSuccessTimes() + "，失败了：" + invocationStatsInfo.getFailedTimes()
                               + ", 发生了异常：" + invocationStatsInfo.getExceptionTimes() + "，平均耗时："
                               + invocationStatsInfo.getAverageMillis() + "ms.");
        }

    }
}
