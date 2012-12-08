package com.murdock.tools.enhancedmit.test;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

public class ClassNameTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Method method = ClassNameTest.class.getMethods()[0];
        long start = System.currentTimeMillis();

        for (int i = 0; i < 15000; i++) {
            method.getDeclaringClass().getSimpleName();
        }
        
        System.out.println(System.currentTimeMillis() - start);

        start = System.currentTimeMillis();

        for (int i = 0; i < 10000; i++) {
            method.getDeclaringClass().getSimpleName();
        }

        System.out.println(System.currentTimeMillis() - start);

        map.putIfAbsent(method, method.getDeclaringClass().getSimpleName());

        start = System.currentTimeMillis();

        for (int i = 0; i < 10000; i++) {
            map.get(method);
        }

        System.out.println(System.currentTimeMillis() - start);

    }

    private static ConcurrentHashMap<Method, String> map = new ConcurrentHashMap<Method, String>();

    public void xx() {

    }

}
