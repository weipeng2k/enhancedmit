/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.murdock.tools.invocationstats;

/**
 * <pre>
 * 调用统计回调
 * 
 * </pre>
 * 
 * @author weipeng 2012-11-1 下午8:09:03
 */
public interface InvocationStatsExecutor {

    /**
     * <pre>
     * 回调处理方法
     * 
     * </pre>
     * 
     * @param invocationStatsInfo
     */
    void execute(InvocationStatsInfo invocationStatsInfo);
}
