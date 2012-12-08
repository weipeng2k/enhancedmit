/**
 * 
 */
package com.murdock.tools.enhancedmit.handler;

/**
 * @author weipeng
 * 
 */
public class InvocationCostUtils {
	private static final ThreadLocal<Long> START_TIME = new ThreadLocal<Long>() {
		@Override
		protected Long initialValue() {
			return System.nanoTime();
		}
	};

	public static void startProfile() {
		START_TIME.set(System.nanoTime());
	}

	public static long endProfile() {
		return System.nanoTime() - START_TIME.get();
	}
}
