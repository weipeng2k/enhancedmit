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
@HandlerConfig(name = "systemPrintBeforeExecutionHandler", belongsTo = CapabilityTypeEnum.SYSTEM_PRINT, isDefault = true)
public class SystemPrintBeforeExecutionHandler implements
		BeforeExecutionHandler {

	public void handle(MethodInvocation methodInvokeInfo) {
		System.out.println("SystemPrintBeforeExecutionHandler:" + methodInvokeInfo + "param is " + methodInvokeInfo.getArguments());
	}

}
