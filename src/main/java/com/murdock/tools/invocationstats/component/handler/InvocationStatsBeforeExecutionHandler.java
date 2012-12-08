/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.murdock.tools.invocationstats.component.handler;

import org.aopalliance.intercept.MethodInvocation;

import com.murdock.tools.enhancedmit.annotation.HandlerConfig;
import com.murdock.tools.enhancedmit.domain.extension.BeforeExecutionHandler;
import com.murdock.tools.enhancedmit.enums.CapabilityTypeEnum;
import com.murdock.tools.invocationstats.component.InvocationStatsComponent;
import com.murdock.tools.invocationstats.util.MethodProfileUtils;

/**
 * <pre>
 * 方法调用统计的前置处理器
 * 
 * 在这里需要做：
 * (1) 首先将该方法的调用次数加一
 * (2) 对该方法的profile开始
 * 
 * </pre>
 * 
 * @author weipeng 2012-11-2 下午2:58:27
 */
@HandlerConfig(belongsTo = CapabilityTypeEnum.INVOCATION_STATS, name = "InvocationStatsBeforeExecutionHandler", isDefault = true)
public class InvocationStatsBeforeExecutionHandler implements BeforeExecutionHandler {

    /**
     * 调用统计组件
     */
    private InvocationStatsComponent invocationStatsComponent;

    /*
     * (non-Javadoc)
     * @see com.murdock.tools.enhancedmit.domain.extension.BeforeExecutionHandler#handle(org.aopalliance.intercept.
     * MethodInvocation)
     */
    @Override
    public void handle(MethodInvocation methodInvocation) {
        if (methodInvocation != null && methodInvocation.getMethod() != null) {
            invocationStatsComponent.addInvokeTimes(methodInvocation.getMethod(), 1);
            MethodProfileUtils.start();
        }
    }

    // ---------------------------Getters and Setters -----------------------------//
    public void setInvocationStatsComponent(InvocationStatsComponent invocationStatsComponent) {
        this.invocationStatsComponent = invocationStatsComponent;
    }
    // ---------------------------Getters and Setters -----------------------------//

}
