/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.murdock.tools.invocationstats.util;

/**
 * @author weipeng 2012-11-2 下午3:04:30
 */
public class MethodProfileUtils {

    private static final ThreadLocal<Long> METHOD_PER_INVOCATION = new ThreadLocal<Long>() {

                                                                     @Override
                                                                     protected Long initialValue() {
                                                                         return System.currentTimeMillis();
                                                                     }

                                                                 };

    /**
     * <pre>
     * 该方法的调用开始计时，当前线程开始
     * 
     * </pre>
     */
    public static final void start() {
        METHOD_PER_INVOCATION.set(System.currentTimeMillis());
    }

    /**
     * <pre>
     * 当前线程调用结束
     * 
     * </pre>
     * 
     * @return
     */
    public static final long end() {
        return System.currentTimeMillis() - METHOD_PER_INVOCATION.get();
    }

}
