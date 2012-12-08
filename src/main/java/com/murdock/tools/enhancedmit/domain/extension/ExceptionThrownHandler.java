/**
 * 
 */
package com.murdock.tools.enhancedmit.domain.extension;

import org.aopalliance.intercept.MethodInvocation;

import com.murdock.tools.enhancedmit.domain.Handler;

/**
 * <pre>
 * 异常处理器
 * 
 * 如果调用过程中发生了异常，那么将会被该处理器捕获，可以对异常进行分析，当然也可以LOG等。
 * 
 * </pre>
 * 
 * @author weipeng
 * 
 */
public interface ExceptionThrownHandler extends Handler {
	/**
	 * <pre>
	 * 异常处理
	 * 
	 * </pre>
	 * 
	 * @param methodInvocation
	 * @param exception
	 */
	void handle(MethodInvocation methodInvocation, Throwable exception);
}
