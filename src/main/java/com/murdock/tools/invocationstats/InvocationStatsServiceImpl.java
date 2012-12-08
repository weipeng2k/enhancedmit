/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.murdock.tools.invocationstats;

import java.lang.reflect.Method;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.murdock.tools.enhancedmit.domain.Handler;
import com.murdock.tools.invocationstats.component.InvocationStatsComponent;
import com.murdock.tools.invocationstats.util.InvocationStatsServiceUtils;

/**
 * <pre>
 * 方法调用统计服务
 * 
 * 使用了ScheduledThreadPoolExecutor做定时调度的实现
 * 
 * </pre>
 * 
 * @author weipeng 2012-11-2 上午9:54:49
 * @version 1.1 在提交一个JOB时才初始化线程池
 * @version 1.2 将方法列表进行返回并支持静态工具
 */
public class InvocationStatsServiceImpl implements InvocationStatsService,
	DisposableBean, InitializingBean {

    /**
     * LOG
     */
    private static Log log = LogFactory.getLog(Handler.class);
    /**
     * 调用统计的组件
     */
    private InvocationStatsComponent invocationStatsComponent;
    /**
     * 单个线程完成按时间进行调度
     */
    private ScheduledThreadPoolExecutor threadPool;
    /**
     * 开始的状态
     */
    private volatile boolean start;
    /**
     * Lock
     */
    private Lock lock = new ReentrantLock();

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
	InvocationStatsServiceUtils.setInstance(this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.beans.factory.DisposableBean#destroy()
     */
    @Override
    public void destroy() throws Exception {
	// 仅仅是终结
	if (threadPool != null) {
	    threadPool.shutdownNow();
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.murdock.tools.invocationstats.InvocationStatsService#fetchInvocationStats
     * (java.lang.reflect.Method)
     */
    @Override
    public InvocationStatsInfo fetchInvocationStats(Method method) {
	InvocationStatsInfo result = null;

	if (method != null) {
	    result = new InvocationStatsInfo();
	    result.setMethodName(method.getName());
	    result.setParameterTypes(method.getParameterTypes());

	    // 设置调用统计信息
	    long currentInvokeTimes = invocationStatsComponent
		    .currentInvokeTimes(method, true);
	    long currentSuccessTimes = invocationStatsComponent
		    .currentSuccessTimes(method, true);
	    long currentFailedTimes = invocationStatsComponent
		    .currentFailedTimes(method, true);
	    long currentExceptionThrownTimes = invocationStatsComponent
		    .currentExceptionTimes(method, true);
	    long currentSpendMillis = invocationStatsComponent
		    .currentSpendMillis(method, true);
	    long currentExtraCounts = invocationStatsComponent
		    .currentExtraCounts(method, true);
	    result.setInvocationTimes(currentInvokeTimes);
	    result.setSuccessTimes(currentSuccessTimes);
	    result.setFailedTimes(currentFailedTimes);
	    result.setExceptionTimes(currentExceptionThrownTimes);
	    if (currentInvokeTimes > 0) {
		result.setAverageMillis(currentSpendMillis / currentInvokeTimes);
	    }
	    result.setExtraCounts(currentExtraCounts);
	}

	return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.murdock.tools.invocationstats.InvocationStatsService#
     * crontabExecuteAllStatsMethods(com.murdock.tools. invocationstats
     * .InvocationStatsExecutor, int)
     */
    @Override
    public void crontabExecuteAllStatsMethods(InvocationStatsExecutor executor,
	    int intervalSeconds) {
	if (executor != null && intervalSeconds > 0) {
	    try {
		lock.lock();
		if (!start) {
		    threadPool = new ScheduledThreadPoolExecutor(1);
		    ExecuteJob job = new ExecuteJob(executor);
		    threadPool.scheduleAtFixedRate(job, intervalSeconds,
			    intervalSeconds, TimeUnit.SECONDS);
		    start = true;
		}
	    } finally {
		lock.unlock();
	    }
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.murdock.tools.invocationstats.InvocationStatsService#currentStatsMethods
     * ()
     */
    @Override
    public Method[] currentStatsMethods() {
	return invocationStatsComponent.currentStatsMethods();
    }

    /*
     * 执行任务
     */
    private class ExecuteJob implements Runnable {

	private InvocationStatsExecutor executor;

	public ExecuteJob(InvocationStatsExecutor executor) {
	    this.executor = executor;
	}

	@Override
	public void run() {
	    Method[] statsMethods = invocationStatsComponent
		    .currentStatsMethods();
	    if (statsMethods != null && statsMethods.length > 0) {
		for (Method method : statsMethods) {
		    try {
			executor.execute(fetchInvocationStats(method));
		    } catch (Exception ex) {
			log.error(
				"[InvocationStatsServiceImpl.ExecuteJob.run] got exception",
				ex);
		    }
		}
	    }
	}
    }

    // -----------------Getters and Setters--------------//
    public void setInvocationStatsComponent(
	    InvocationStatsComponent invocationStatsComponent) {
	this.invocationStatsComponent = invocationStatsComponent;
    }
    // -----------------Getters and Setters--------------//
}
