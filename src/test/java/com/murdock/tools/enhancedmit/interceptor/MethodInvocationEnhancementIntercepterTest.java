/**
 * 
 */
package com.murdock.tools.enhancedmit.interceptor;

import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.murdock.tools.enhancedmit.test.Clazz;
import com.murdock.tools.enhancedmit.test.Interface;
import com.murdock.tools.invocationstats.InvocationStatsExecutor;
import com.murdock.tools.invocationstats.InvocationStatsInfo;
import com.murdock.tools.invocationstats.InvocationStatsService;

/**
 * @author weipeng
 */
@ContextConfiguration(locations = { "classpath:aop.xml" })
public class MethodInvocationEnhancementIntercepterTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private Clazz                  clazz;

    @Autowired
    private Interface              interfaces;

    @Autowired
    private InvocationStatsService invocationStatsService;

    @Test
    public void create() {
        // System.out.println(clazz);

        clazz.returN("xxx");
        //
        // long start = System.currentTimeMillis();
        //
        // clazz.returN("sfsdfsf");
        //
        // System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void test() {
        interfaces.test(1);
    }

    @Test
    public void testPrivate() {
        clazz.withoutEnhancement();
    }
    
    @Test
    public void tesvate() {
        clazz.returN(1);
        
        clazz.returN("d");
    }

    @Test
    public void exception() {
        try {
            interfaces.exception();
        } catch (Exception ex) {

        }
    }

    @Test
    public void add() {
        AtomicLong l = new AtomicLong(Long.MAX_VALUE - 1);
        System.out.println(l.addAndGet(1));
        System.out.println(l.addAndGet(1));
        System.out.println(l.addAndGet(1));
        System.out.println(l.addAndGet(1));
    }

    @Test
    public void test1() {
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread() {

                @Override
                public void run() {
                    while (true) {
                        try {
                            interfaces.echo(null);
                            clazz.exception();
                            interfaces.test(1);
                        } catch (Exception ex) {
                            
                        }
                    }
                }

            };
            thread.start();
        }

        invocationStatsService.crontabExecuteAllStatsMethods(new PrintExecutor(), 20);

        try {
            Thread.sleep(5 * 60 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void test2() {
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread() {

                @Override
                public void run() {
                    while (true) {
                        try {
                            interfaces.test(1);
                        } catch (Exception ex) {
                            
                        }
                    }
                }

            };
            thread.start();
        }

        invocationStatsService.crontabExecuteAllStatsMethods(new PrintExecutor(), 20);

        try {
            Thread.sleep(5 * 60 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
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
            System.out.println("===========================================================");
        }

    }

}
