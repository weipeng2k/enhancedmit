/**
 * 
 */
package com.murdock.tools.enhancedmit.handler;

import org.aopalliance.intercept.MethodInvocation;

import com.murdock.tools.enhancedmit.annotation.HandlerConfig;
import com.murdock.tools.enhancedmit.domain.extension.AfterExecutionHandler;
import com.murdock.tools.enhancedmit.enums.CapabilityTypeEnum;

/**
 * @author weipeng
 * 
 */
@HandlerConfig(name = "InvocationCostTimeAfterExecutionHandler", isDefault = true, belongsTo = CapabilityTypeEnum.INVOCATION_COST_TIME)
public class InvocationCostTimeAfterExecutionHandler implements
		AfterExecutionHandler {

	@Override
	public void handle(MethodInvocation methodInvocation, Object result) {
		long nano = InvocationCostUtils.endProfile();
		System.out.println(methodInvocation.getMethod().getName() + "调用耗时："
				+ nano + " ns.");
	}

}
