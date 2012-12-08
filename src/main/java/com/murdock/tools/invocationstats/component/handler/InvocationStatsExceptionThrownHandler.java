/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.murdock.tools.invocationstats.component.handler;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;

import com.murdock.tools.enhancedmit.annotation.HandlerConfig;
import com.murdock.tools.enhancedmit.domain.extension.ExceptionThrownHandler;
import com.murdock.tools.enhancedmit.enums.CapabilityTypeEnum;
import com.murdock.tools.invocationstats.component.InvocationStatsComponent;
import com.murdock.tools.invocationstats.util.MethodProfileUtils;

/**
 * <pre>
 * 调用统计异常处理器
 * 
 * 所做主要工作：
 * (1) 异常数加一
 * (2) 如果异常是接口允许抛出的那么可以容忍成功加一，否则失败次数加一
 * (3) profile end
 * 
 * </pre>
 * 
 * @author weipeng 2012-11-2 下午5:07:19
 */
@HandlerConfig(belongsTo = CapabilityTypeEnum.INVOCATION_STATS, name = "InvocationStatsExceptionThrownHandler", isDefault = true)
public class InvocationStatsExceptionThrownHandler implements ExceptionThrownHandler {

    /**
     * 调用统计组件
     */
    private InvocationStatsComponent invocationStatsComponent;

    /*
     * (non-Javadoc)
     * @see com.murdock.tools.enhancedmit.domain.extension.ExceptionThrownHandler#handle(org.aopalliance.intercept.
     * MethodInvocation, java.lang.Throwable)
     */
    @Override
    public void handle(MethodInvocation methodInvocation, Throwable exception) {
        if (methodInvocation != null && methodInvocation.getMethod() != null) {
            Method method = methodInvocation.getMethod();
            invocationStatsComponent.addExceptionTimes(method, 1);
            invocationStatsComponent.addSpendMillis(method, MethodProfileUtils.end());
            Class<?>[] clazzes = method.getExceptionTypes();

            // 不会为空
            if (clazzes != null) {
                // 没有声明异常，却抛出来了
                if (clazzes.length <= 0) {
                    invocationStatsComponent.addFailedTimes(method, 1);
                } else {
                    boolean declaredException = false;

                    for (Class<?> clazz : clazzes) {
                        if (clazz.isInstance(exception)) {
                            // 接口声明了该异常，那么break;
                            declaredException = true;
                            break;
                        }
                    }

                    // 如果已经声明了，那么就算成功的
                    if (declaredException) {
                        invocationStatsComponent.addSuccessTimes(method, 1);
                    } else {
                        invocationStatsComponent.addFailedTimes(method, 1);
                    }
                }
            }

        }
    }

    // ---------------------------Getters and Setters -----------------------------//
    public void setInvocationStatsComponent(InvocationStatsComponent invocationStatsComponent) {
        this.invocationStatsComponent = invocationStatsComponent;
    }
    // ---------------------------Getters and Setters -----------------------------//

}
