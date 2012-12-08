/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.murdock.tools.invocationstats.component;

import java.lang.reflect.Method;
import java.util.concurrent.CountDownLatch;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * @author weipeng 2012-11-1 下午5:35:54
 */
@ContextConfiguration(locations = { "classpath:invocationStatsComponent.xml" })
public class InvocationStatsComponentTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private InvocationStatsComponent component;

    @Test
    public void create() {
        TestCase.assertEquals(component != null, true);
    }

    @Test
    public void addInvocation() {
        System.out.println(component.addInvokeTimes(InvocationStatsComponent.class.getMethods()[0], 2));
    }

    @Test
    public void currentInvocation() {
        System.out.println(component.addInvokeTimes(InvocationStatsComponent.class.getMethods()[0], 2));
        System.out.println(component.currentInvokeTimes(InvocationStatsComponent.class.getMethods()[0], false));
        System.out.println(component.currentInvokeTimes(InvocationStatsComponent.class.getMethods()[0], false));
        System.out.println(component.currentInvokeTimes(InvocationStatsComponent.class.getMethods()[0], true));
        System.out.println(component.currentInvokeTimes(InvocationStatsComponent.class.getMethods()[0], true));
    }

    @Test
    public void currentStatsMethods() {
        System.out.println(component.currentStatsMethods());
        System.out.println(component.currentStatsMethods());
        System.out.println(component.addInvokeTimes(InvocationStatsComponent.class.getMethods()[0], 2));
        System.out.println(component.currentStatsMethods());
        System.out.println(component.currentStatsMethods());
    }

    @Test
    public void performance() {
        Method method = InvocationStatsComponent.class.getMethods()[0];

        CountDownLatch over = new CountDownLatch(10);
        CountDownLatch start = new CountDownLatch(1);

        for (int i = 0; i < 10; i++) {
            Job job = new Job();
            job.cdl = start;
            job.over = over;
            job.method = method;
            Thread thread = new Thread(job);
            thread.start();
        }

        start.countDown();
        long startT = System.currentTimeMillis();
        try {
            over.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(System.currentTimeMillis() - startT);

        System.out.println(component.currentInvokeTimes(method, true));
        System.out.println(component.currentInvokeTimes(method, true));

    }

    class Job implements Runnable {

        CountDownLatch cdl;

        CountDownLatch over;

        Method         method;

        @Override
        public void run() {

            try {
                cdl.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < 10000; i++) {
                component.addInvokeTimes(method, 10);
            }

            over.countDown();
        }
    }

}
