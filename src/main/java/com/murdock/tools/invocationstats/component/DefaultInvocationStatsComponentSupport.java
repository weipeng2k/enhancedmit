/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.murdock.tools.invocationstats.component;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <pre>
 * 默认调用统计的实现，使用ConcurrentHashMap来完成方法和统计信息的存储工作
 * 
 * </pre>
 * 
 * @author weipeng 2012-11-1 下午5:26:03
 * @version 1.0 提供原始支持
 * @version 1.1 提供了业务额外参数支持
 */
public class DefaultInvocationStatsComponentSupport implements InvocationStatsComponent {

    /**
     * 空的方法数组
     */
    private static final Method[]                            EMPTY_METHOD_ARRAY    = new Method[] {};
    /**
     * 空的调用统计
     */
    private static final InvocationStats                     NULL_INVOCATION_STATS = new InvocationStats();
    /**
     * 存储方法对应的统计信息
     */
    private final ConcurrentHashMap<Method, InvocationStats> cachedStats           = new ConcurrentHashMap<Method, InvocationStats>();

    /*
     * (non-Javadoc)
     * @see
     * com.murdock.tools.invocationstats.component.InvocationStatsComponent#currentInvokeTimes(java.lang.reflect.Method,
     * boolean)
     */
    @Override
    public long currentInvokeTimes(Method method, boolean reset) {
        InvocationStats is = fetchInvocationStats(method);

        if (reset) {
            return is.invokeTimes.getAndSet(0);
        }

        return is.invokeTimes.get();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.murdock.tools.invocationstats.component.InvocationStatsComponent#addInvokeTimes(java.lang.reflect.Method,
     * long)
     */
    @Override
    public long addInvokeTimes(Method method, long delta) {
        InvocationStats is = fetchInvocationStats(method);

        return is.invokeTimes.addAndGet(delta);
    }

    /*
     * (non-Javadoc)
     * @see
     * com.murdock.tools.invocationstats.component.InvocationStatsComponent#currentSuccessTimes(java.lang.reflect.Method
     * , boolean)
     */
    @Override
    public long currentSuccessTimes(Method method, boolean reset) {
        InvocationStats is = fetchInvocationStats(method);

        if (reset) {
            return is.successTimes.getAndSet(0);
        }

        return is.successTimes.get();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.murdock.tools.invocationstats.component.InvocationStatsComponent#addSuccessTimes(java.lang.reflect.Method,
     * long)
     */
    @Override
    public long addSuccessTimes(Method method, long delta) {
        InvocationStats is = fetchInvocationStats(method);

        return is.successTimes.addAndGet(delta);
    }

    /*
     * (non-Javadoc)
     * @see
     * com.murdock.tools.invocationstats.component.InvocationStatsComponent#currentFailedTimes(java.lang.reflect.Method,
     * boolean)
     */
    @Override
    public long currentFailedTimes(Method method, boolean reset) {
        InvocationStats is = fetchInvocationStats(method);

        if (reset) {
            return is.failedTimes.getAndSet(0);
        }

        return is.failedTimes.get();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.murdock.tools.invocationstats.component.InvocationStatsComponent#addFailedTimes(java.lang.reflect.Method,
     * long)
     */
    @Override
    public long addFailedTimes(Method method, long delta) {
        InvocationStats is = fetchInvocationStats(method);

        return is.failedTimes.addAndGet(delta);
    }

    /*
     * (non-Javadoc)
     * @see
     * com.murdock.tools.invocationstats.component.InvocationStatsComponent#currentExceptionTimes(java.lang.reflect.
     * Method, boolean)
     */
    @Override
    public long currentExceptionTimes(Method method, boolean reset) {
        InvocationStats is = fetchInvocationStats(method);

        if (reset) {
            return is.exceptionTimes.getAndSet(0);
        }

        return is.exceptionTimes.get();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.murdock.tools.invocationstats.component.InvocationStatsComponent#addExceptionTimes(java.lang.reflect.Method,
     * long)
     */
    @Override
    public long addExceptionTimes(Method method, long delta) {
        InvocationStats is = fetchInvocationStats(method);

        return is.exceptionTimes.addAndGet(delta);
    }

    /*
     * (non-Javadoc)
     * @see
     * com.murdock.tools.invocationstats.component.InvocationStatsComponent#currentSpendMillis(java.lang.reflect.Method,
     * boolean)
     */
    @Override
    public long currentSpendMillis(Method method, boolean reset) {
        InvocationStats is = fetchInvocationStats(method);

        if (reset) {
            return is.spendMillis.getAndSet(0);
        }

        return is.spendMillis.get();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.murdock.tools.invocationstats.component.InvocationStatsComponent#addSpendMillis(java.lang.reflect.Method,
     * long)
     */
    @Override
    public long addSpendMillis(Method method, long millis) {
        InvocationStats is = fetchInvocationStats(method);

        return is.spendMillis.addAndGet(millis);
    }

    /*
     * (non-Javadoc)
     * @see
     * com.murdock.tools.invocationstats.component.InvocationStatsComponent#currentExtraCounts(java.lang.reflect.Method,
     * boolean)
     */
    @Override
    public long currentExtraCounts(Method method, boolean reset) {
        InvocationStats is = fetchInvocationStats(method);

        if (reset) {
            return is.extraCounts.getAndSet(0);
        }

        return is.extraCounts.get();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.murdock.tools.invocationstats.component.InvocationStatsComponent#addExtraCounts(java.lang.reflect.Method,
     * long)
     */
    @Override
    public long addExtraCounts(Method method, long delta) {
        InvocationStats is = fetchInvocationStats(method);
        
        return is.extraCounts.addAndGet(delta);
    }

    /*
     * (non-Javadoc)
     * @see com.murdock.tools.invocationstats.component.InvocationStatsComponent#currentStatsMethods()
     */
    @Override
    public Method[] currentStatsMethods() {
        return cachedStats.keySet() != null ? cachedStats.keySet().toArray(EMPTY_METHOD_ARRAY) : EMPTY_METHOD_ARRAY;
    }

    /**
     * <pre>
     * 获取一个方法统计信息
     * 
     * </pre>
     * 
     * @param method
     * @return
     */
    private InvocationStats fetchInvocationStats(Method method) {
        if (method == null) {
            return NULL_INVOCATION_STATS;
        }

        InvocationStats is = cachedStats.get(method);

        // 没有找到，构造一个新的，如果在同一时刻已经有了，那么将已有的返回
        if (is == null) {
            is = new InvocationStats();
            InvocationStats oldOne = cachedStats.putIfAbsent(method, is);

            if (oldOne != null) {
                is = oldOne;
            }
        }

        return is;
    }
}
