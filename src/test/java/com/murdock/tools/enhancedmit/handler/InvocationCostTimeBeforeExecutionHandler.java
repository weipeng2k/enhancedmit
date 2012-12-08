/**
 * 
 */
package com.murdock.tools.enhancedmit.handler;

import org.aopalliance.intercept.MethodInvocation;

import com.murdock.tools.enhancedmit.annotation.HandlerConfig;
import com.murdock.tools.enhancedmit.domain.extension.BeforeExecutionHandler;
import com.murdock.tools.enhancedmit.enums.CapabilityTypeEnum;

/**
 * @author weipeng
 * 
 */
@HandlerConfig(name = "InvocationCostTimeBeforeExecutionHandler", isDefault = true, belongsTo = CapabilityTypeEnum.INVOCATION_COST_TIME)
public class InvocationCostTimeBeforeExecutionHandler implements
		BeforeExecutionHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.murdock.tools.enhancedmit.domain.extension.BeforeExecutionHandler
	 * #handle(org.aopalliance.intercept.MethodInvocation)
	 */
	@Override
	public void handle(MethodInvocation methodInvocation) {
		InvocationCostUtils.startProfile();
	}

}
