package com.murdock.tools.enhancedmit.test;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.junit.Test;

/**
 * @author weipeng 2012-11-6 上午9:22:56
 */
public class ClassArrayTest {

    public void windows(String m) {

    }

    @Test
    public void classArray() {
        Class<?>[] t1 = new Class<?>[] { String.class };

        Method method = ClassArrayTest.class.getMethods()[0];
        Class<?>[] t2 = method.getParameterTypes();

        System.out.println(Arrays.equals(t1, t2));
    }

    /**
     * 513384 / 10000 = 51ns
     */
    @Test
    public void performance() {
        Method method = ClassArrayTest.class.getMethods()[0];
        for (int i = 0; i < 30000; i++) {
            method.getParameterTypes();
        }

        long start = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            method.getParameterTypes();
        }
        System.out.println(System.nanoTime() - start);

    }

}
