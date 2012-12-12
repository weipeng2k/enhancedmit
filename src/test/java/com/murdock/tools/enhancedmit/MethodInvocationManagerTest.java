/**
 * 
 */
package com.murdock.tools.enhancedmit;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import junit.framework.TestCase;

import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.murdock.tools.enhancedmit.domain.MethodInvocationEnhancement;
import com.murdock.tools.enhancedmit.test.Clazz;

/**
 * @author weipeng
 * 
 */
@ContextConfiguration(locations = { "classpath:methodInvocationManager.xml" })
public class MethodInvocationManagerTest extends
		AbstractJUnit4SpringContextTests {
	@Autowired
	private MethodInvocationManager methodInvocationManager;

	@Test
	public void create() {
		TestCase.assertEquals(methodInvocationManager != null, true);
	}

	@Test
	public void fetchEnhancement() throws Exception {
		Method method = Clazz.class.getMethod("noreturn", new Class[] {});
		long start = System.currentTimeMillis();
		MethodInvocationEnhancement enhancement = methodInvocationManager
				.fetchEnhancement(method);
		System.out.println("cost" + (System.currentTimeMillis() - start));
		System.out.println(enhancement);
	}

//	@Test
//	public void concurrentFetch() throws Exception {
//		Set<MethodInvocationEnhancement> resultCollector = Collections
//				.synchronizedSet(new HashSet<MethodInvocationEnhancement>());
//		CountDownLatch countDownLatch = new CountDownLatch(1);
//		Method method = Clazz.class.getMethod("noreturn", new Class[] {});
//
//		for (int i = 0; i < 50; i++) {
//			FetchJob job = new FetchJob();
//			job.countDown = countDownLatch;
//			job.method = method;
//			job.resultCollector = resultCollector;
//			job.num = i;
//			Thread thread = new Thread(job);
//			thread.start();
//		}
//
//		countDownLatch.countDown();
//
//		Thread.sleep(2000);
//
//		TestCase.assertEquals(resultCollector.size() == 1, true);
//	}

	@Test
	public void beforeExecution() throws Exception {
		Method method = Clazz.class.getMethod("noreturn", new Class[] {});
		MethodInvocationEnhancement enhancement = methodInvocationManager
				.fetchEnhancement(method);

		MethodInvocation info = new MethodInvocation() {

			@Override
			public Object[] getArguments() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Object proceed() throws Throwable {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Object getThis() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public AccessibleObject getStaticPart() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Method getMethod() {
				// TODO Auto-generated method stub
				return null;
			}

		};
	}

	class FetchJob implements Runnable {
		Method method;
		CountDownLatch countDown;
		Set<MethodInvocationEnhancement> resultCollector;
		int num;

		@Override
		public void run() {
			try {
				countDown.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			MethodInvocationEnhancement enhancement = methodInvocationManager
					.fetchEnhancement(method);

			TestCase.assertEquals(enhancement != null, true);

			resultCollector.add(enhancement);
			System.out.println("Done." + num);
		}

	}

}
