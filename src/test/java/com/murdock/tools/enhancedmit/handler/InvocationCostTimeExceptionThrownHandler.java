/**
 * 
 */
package com.murdock.tools.enhancedmit.handler;

import org.aopalliance.intercept.MethodInvocation;

import com.murdock.tools.enhancedmit.annotation.HandlerConfig;
import com.murdock.tools.enhancedmit.domain.extension.ExceptionThrownHandler;
import com.murdock.tools.enhancedmit.enums.CapabilityTypeEnum;

/**
 * @author weipeng
 * 
 */
@HandlerConfig(name = "InvocationCostTimeExceptionThrownHandler", isDefault = true, belongsTo = CapabilityTypeEnum.INVOCATION_COST_TIME)
public class InvocationCostTimeExceptionThrownHandler implements
		ExceptionThrownHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.murdock.tools.enhancedmit.domain.extension.ExceptionThrownHandler
	 * #handle(org.aopalliance.intercept.MethodInvocation, java.lang.Throwable)
	 */
	@Override
	public void handle(MethodInvocation methodInvocation, Throwable exception) {
		long nano = InvocationCostUtils.endProfile();
		System.out.println(methodInvocation.getMethod().getName() + "调用耗时："
				+ nano + " ns.并且抛出了异常。。。" + exception);
	}

}
