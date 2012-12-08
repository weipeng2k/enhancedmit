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
@HandlerConfig(isDefault = false, belongsTo = CapabilityTypeEnum.SYSTEM_PRINT, name = "SystemPrint2BeforeExecutionHandler")
public class SystemPrint2BeforeExecutionHandler implements
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
		if (methodInvocation != null && methodInvocation.getArguments() != null
				&& methodInvocation.getArguments().length > 0) {
			System.out.println("调用参数：" + methodInvocation.getArguments()[0]);
			System.out.println("调用参数：" + methodInvocation.getArguments()[0]);
		}
	}
}
