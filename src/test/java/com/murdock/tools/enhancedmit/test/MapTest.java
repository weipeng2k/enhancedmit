/**
 * 
 */
package com.murdock.tools.enhancedmit.test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;

/**
 * @author weipeng
 * 
 */
public class MapTest {
	ConcurrentHashMap<String, String> map = new ConcurrentHashMap<String, String>();
	Map<String, String> map2 = new HashMap<String, String>();
	
	@Test
	public void test() {
		map.put("a", "b");

		for (int i = 0; i < 10000; i++) {
			map.get("a");
		}

		map2.put("a", "b");
		for (int i = 0; i < 10000; i++) {
			map2.get("a");
		}
		
		long start = System.nanoTime();
		for (int i = 0; i < 100000; i++) {
			map.get("a");
		}
		System.out.println(System.nanoTime() - start);
		
		start = System.nanoTime();
		for (int i = 0; i < 100000; i++) {
			map2.get("a");
		}
		System.out.println(System.nanoTime() - start);
	}
	
	@Test
	public void method() {
		Method method = this.getClass().getMethods()[0];
		
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			method.getParameterTypes();
		}
		System.out.println(System.currentTimeMillis() - start);
		
		
		
	}
}
