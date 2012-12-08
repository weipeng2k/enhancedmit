/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.murdock.tools.invocationstats.component;

import java.util.concurrent.atomic.AtomicLong;

/**
 * <pre>
 * 调用统计，每个方法将会有一个该对象与其对应
 * 
 * 如果调用失败不是异常导致的，那么这个公式将是成立的：
 * 
 * 一个周期内：
 * invokeTimes = successTimes + failedTimes
 * 总共花费了spendMillis时间
 * 
 * 额外定义了一个附加的统计工具，这个可以对应方法的自定义统计，可以是业务相关的，这样做其实违背了invocationstats的本意
 * 但是，确是显示其扩展能力的绝佳体现。
 * 
 * </pre>
 * 
 * @author weipeng 2012-11-1 下午5:19:10
 */
final class InvocationStats {

    /**
     * 调用的次数
     */
    final AtomicLong invokeTimes    = new AtomicLong();
    /**
     * 调用成功的次数
     */
    final AtomicLong successTimes   = new AtomicLong();
    /**
     * 调用失败的次数
     */
    final AtomicLong failedTimes    = new AtomicLong();
    /**
     * 调用异常的次数
     */
    final AtomicLong exceptionTimes = new AtomicLong();
    /**
     * 花费的时间，毫秒
     */
    final AtomicLong spendMillis    = new AtomicLong();
    /**
     * 额外的计数，次数
     */
    final AtomicLong extraCounts    = new AtomicLong();
    
}
