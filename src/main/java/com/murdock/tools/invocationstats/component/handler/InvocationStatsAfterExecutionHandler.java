/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.murdock.tools.invocationstats.component.handler;

import org.aopalliance.intercept.MethodInvocation;

import com.murdock.tools.enhancedmit.annotation.HandlerConfig;
import com.murdock.tools.enhancedmit.domain.extension.AfterExecutionHandler;
import com.murdock.tools.enhancedmit.enums.CapabilityTypeEnum;
import com.murdock.tools.invocationstats.component.InvocationStatsComponent;
import com.murdock.tools.invocationstats.util.MethodProfileUtils;

/**
 * <pre>
 * 方法统计调用完成处理器
 * 
 * 主要工作：
 * (1)默认调用成功加一
 * (2)profile结束
 * 
 * </pre>
 * 
 * @author weipeng 2012-11-2 下午3:24:10
 */
@HandlerConfig(belongsTo = CapabilityTypeEnum.INVOCATION_STATS, name = "InvocationStatsAfterExecutionHandler", isDefault = true)
public class InvocationStatsAfterExecutionHandler implements AfterExecutionHandler {

    /**
     * 调用统计组件
     */
    private InvocationStatsComponent invocationStatsComponent;

    /*
     * (non-Javadoc)
     * @see com.murdock.tools.enhancedmit.domain.extension.AfterExecutionHandler#handle(org.aopalliance.intercept.
     * MethodInvocation, java.lang.Object)
     */
    @Override
    public void handle(MethodInvocation methodInvocation, Object result) {
        if (methodInvocation != null && methodInvocation.getMethod() != null) {
            invocationStatsComponent.addSuccessTimes(methodInvocation.getMethod(), 1);
            invocationStatsComponent.addSpendMillis(methodInvocation.getMethod(), MethodProfileUtils.end());
        }
    }

    // ---------------------------Getters and Setters -----------------------------//
    public void setInvocationStatsComponent(InvocationStatsComponent invocationStatsComponent) {
        this.invocationStatsComponent = invocationStatsComponent;
    }
    // ---------------------------Getters and Setters -----------------------------//

}
