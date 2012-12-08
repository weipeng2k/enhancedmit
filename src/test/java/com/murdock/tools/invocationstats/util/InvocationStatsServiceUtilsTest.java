/**
 * 
 */
package com.murdock.tools.invocationstats.util;

import java.lang.reflect.Method;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;

import com.murdock.tools.invocationstats.InvocationStatsExecutor;
import com.murdock.tools.invocationstats.InvocationStatsInfo;
import com.murdock.tools.invocationstats.InvocationStatsService;

/**
 * @author weipeng
 * 
 */
public class InvocationStatsServiceUtilsTest {

    static A a = new A();

    @Test
    public void get() {
	Thread set = new Thread() {

	    @Override
	    public void run() {
		try {
		    Thread.sleep(5000);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}

		InvocationStatsServiceUtils.setInstance(a);
	    }

	};
	set.start();

	System.out.println(InvocationStatsServiceUtils.getInstance());
	System.out.println(InvocationStatsServiceUtils.getInstance());
	System.out.println(InvocationStatsServiceUtils.getInstance());
    }

    @Test
    public void concurrent() {
	final CountDownLatch cdl = new CountDownLatch(1);

	Thread set = new Thread() {

	    @Override
	    public void run() {

		try {
		    cdl.await();
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}

		InvocationStatsServiceUtils.setInstance(a);
	    }

	};
	set.start();

	Thread get = new Thread() {

	    @Override
	    public void run() {

		try {
		    cdl.await();
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}

		System.out.println(InvocationStatsServiceUtils.getInstance());
	    }

	};
	get.start();

	try {
	    Thread.sleep(1000);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
	cdl.countDown();

    }

    static class A implements InvocationStatsService {

	@Override
	public InvocationStatsInfo fetchInvocationStats(Method method) {
	    return null;
	}

	@Override
	public Method[] currentStatsMethods() {
	    return null;
	}

	@Override
	public void crontabExecuteAllStatsMethods(
		InvocationStatsExecutor executor, int intervalSeconds) {
	}

    }

}
