/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.murdock.tools.invocationstats;

import java.lang.reflect.Method;

/**
 * <pre>
 * 方法调用统计服务，可以使用一个Executor的实现将对InvocationStatsInfo的处理设置到crontab的执行过程中。
 * 
 * 理论上，该服务需要依赖处理逻辑的业务方，但是通过回调将其依赖进行了反转，而这种方式将会使业务代码
 * 从接口实现中隔离开来。
 * 
 * </pre>
 * 
 * @author weipeng 2012-11-1 下午8:08:09
 */
public interface InvocationStatsService {

    /**
     * <pre>
     * 获取方法调用统计对象，将会把这个方法对应的一段时间统计信息设置为0，即重置
     * 
     * </pre>
     * 
     * @param method
     * @return
     */
    InvocationStatsInfo fetchInvocationStats(Method method);

    /**
     * <pre>
     * 获取当前正在统计的方法
     * 
     * </pre>
     * 
     * @return
     */
    Method[] currentStatsMethods();

    /**
     * <pre>
     * 将所有已经进入统计的方法进行周期的执行给定的InvocationStatsExecutor
     * 
     * 执行调用统计执行者的回调，将会按照时间间隔来进行回调
     * 
     * 该方法调用一次后即可开始执行，不会再接受第二个executor，原有是，如果两个executor的间隔时间不一致，后面的执行者就没有办法
     * 看到之前执行的结果，这里可以只用考虑为解耦的需求
     * 
     * </pre>
     * 
     * @param executor
     * @param intervalSeconds
     */
    void crontabExecuteAllStatsMethods(InvocationStatsExecutor executor,
	    int intervalSeconds);
}
