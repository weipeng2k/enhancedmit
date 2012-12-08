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
@HandlerConfig(name = "systemPrintAfterExecutionHandler", belongsTo = CapabilityTypeEnum.SYSTEM_PRINT, isDefault = true)
public class SystemPrintAfterExecutionHandler implements AfterExecutionHandler {

	public void handle(MethodInvocation methodInvocation, Object result) {
		System.out.println("SystemPrintAfterExecutionHandler:"
				+ methodInvocation + " result is " + result);
	}

}
