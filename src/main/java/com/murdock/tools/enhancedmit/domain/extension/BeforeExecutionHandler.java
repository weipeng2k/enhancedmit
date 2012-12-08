/**
 * 
 */
package com.murdock.tools.enhancedmit.domain.extension;

import org.aopalliance.intercept.MethodInvocation;

import com.murdock.tools.enhancedmit.domain.Handler;

/**
 * <pre>
 * 执行方法前的处理器
 * 
 * </pre>
 * 
 * @author weipeng
 * 
 */
public interface BeforeExecutionHandler extends Handler {
	/**
	 * <pre>
	 * 进入调用方法，但是在执行逻辑之前
	 * 
	 * </pre>
	 * 
	 * @param methodInvocation
	 */
	void handle(MethodInvocation methodInvocation);
}
