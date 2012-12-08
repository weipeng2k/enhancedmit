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
@HandlerConfig(name = "systemPrintExceptionThrownHandler", belongsTo = CapabilityTypeEnum.SYSTEM_PRINT, isDefault = true)
public class SystemPrintExceptionThrownHandler implements
		ExceptionThrownHandler {

	public void handle(MethodInvocation methodInvokeInfo, Throwable exception) {
		System.out.println("SystemPrintExceptionThrownHandler:"
				+ methodInvokeInfo + ", exception is " + exception);
	}

}
