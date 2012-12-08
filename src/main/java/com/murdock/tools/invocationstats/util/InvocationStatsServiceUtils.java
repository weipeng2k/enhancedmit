/**
 * 
 */
package com.murdock.tools.invocationstats.util;

import com.murdock.tools.invocationstats.InvocationStatsService;

/**
 * <pre>
 * 该工具完成服务的设置和获取工作，为了方便Spring容器外的class能够获取到这个服务
 * 
 * </pre>
 * 
 * @author weipeng
 * 
 */
public class InvocationStatsServiceUtils {

    /**
     * LOCK，服务没有设置完成时暂时阻塞
     */
    private static final Object LOCK = new Object();
    /**
     * 服务实例
     */
    private static InvocationStatsService instance;

    /**
     * <pre>
     * 获取服务实例，如果服务没有被设置的话(intance == null)，该方法会暂时阻塞住当前的线程
     * 也就是说非Spring容器的对象使用这个Spring的服务时，会等待该服务完成初始化。
     * 
     * </pre>
     * 
     * @return
     */
    public static final InvocationStatsService getInstance() {
	if (instance != null) {
	    return instance;
	}

	synchronized (LOCK) {
	    while (instance == null) {
		try {
		    LOCK.wait();
		} catch (InterruptedException e) {
		}
	    }

	    return instance;
	}
    }

    /**
     * <pre>
     * 设置服务
     * 
     * </pre>
     * 
     * @param instance
     */
    public static final void setInstance(InvocationStatsService instance) {
	synchronized (LOCK) {
	    InvocationStatsServiceUtils.instance = instance;

	    LOCK.notifyAll();
	}
    }
}
