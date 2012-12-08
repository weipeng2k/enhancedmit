/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.murdock.tools.invocationstats.component;

import java.lang.reflect.Method;

/**
 * <pre>
 * 调用统计的组件，该组件提供了对应方法的调用次数、成功次数等信息的统计工作，当然也可以获取这些信息
 * 
 * 该组件的实现需要是线程安全的
 * 
 * </pre>
 * 
 * @author weipeng 2012-11-1 下午4:50:24
 */
public interface InvocationStatsComponent {

    /**
     * <pre>
     * 获取当前的调用次数
     * 
     * </pre>
     * 
     * @param method
     * @param reset 是否重置，如果为true，将会把当前的统计结果设置为0并返回当前已有的统计数目
     * @return 当前统计数目
     */
    long currentInvokeTimes(Method method, boolean reset);

    /**
     * <pre>
     * 添加调用的次数
     * 
     * </pre>
     * 
     * @param method
     * @param delta
     * @return
     */
    long addInvokeTimes(Method method, long delta);

    /**
     * <pre>
     * 获取当前的调用成功的次数
     * 
     * </pre>
     * 
     * @param method
     * @param reset 是否重置，如果为true，将会把当前的统计结果设置为0并返回当前已有的统计数目
     * @return 当前统计数目
     */
    long currentSuccessTimes(Method method, boolean reset);

    /**
     * <pre>
     * 添加调用成功的次数
     * 
     * </pre>
     * 
     * @param method
     * @param delta
     * @return
     */
    long addSuccessTimes(Method method, long delta);

    /**
     * <pre>
     * 获取当前的调用失败的次数
     * 
     * </pre>
     * 
     * @param method
     * @param reset 是否重置，如果为true，将会把当前的统计结果设置为0并返回当前已有的统计数目
     * @return
     */
    long currentFailedTimes(Method method, boolean reset);

    /**
     * <pre>
     * 添加调用失败的次数
     * 
     * </pre>
     * 
     * @param method
     * @param delta
     * @return
     */
    long addFailedTimes(Method method, long delta);

    /**
     * <pre>
     * 获取当前的调用异常的次数
     * 
     * </pre>
     * 
     * @param method
     * @param reset
     * @return
     */
    long currentExceptionTimes(Method method, boolean reset);

    /**
     * <pre>
     * 添加调用异常的次数
     * 
     * </pre>
     * 
     * @param method
     * @param delta
     * @return
     */
    long addExceptionTimes(Method method, long delta);

    /**
     * <pre>
     * 获取当前的调用时间总长度，单位是毫秒
     * 
     * </pre>
     * 
     * @param method
     * @param reset
     * @return
     */
    long currentSpendMillis(Method method, boolean reset);

    /**
     * <pre>
     * 添加调用时间长度，单位是毫秒
     * 
     * </pre>
     * 
     * @param method
     * @param millis
     * @return
     */
    long addSpendMillis(Method method, long millis);

    /**
     * <pre>
     * 获取额外的统计次数，这里可能就是业务相关了
     * 
     * </pre>
     * 
     * @param method
     * @param reset
     * @return
     */
    long currentExtraCounts(Method method, boolean reset);

    /**
     * <pre>
     * 添加额外统计次数，这里可能就是业务相关的了
     * 
     * </pre>
     * 
     * @param method
     * @param delta
     * @return
     */
    long addExtraCounts(Method method, long delta);

    /**
     * <pre>
     * 返回目前正在统计的方法
     * 
     * </pre>
     * 
     * @return
     */
    Method[] currentStatsMethods();

}
