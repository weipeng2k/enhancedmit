/**
 * 
 */
package com.murdock.tools.enhancedmit.domain.extension;

import org.aopalliance.intercept.MethodInvocation;

import com.murdock.tools.enhancedmit.domain.Handler;

/**
 * <pre>
 * 执行完毕后的处理器
 * 
 * 如果方法执行过程中发生了异常，那么将不会进入该处理器，处理器执行中，不应该篡改result对象
 * 
 * </pre>
 * 
 * @author weipeng
 * 
 */
public interface AfterExecutionHandler extends Handler {
	/**
	 * <pre>
	 * 后置处理
	 * 
	 * </pre>
	 * 
	 * @param methodInvocation
	 * @param result 方法调用结果，如果返回值是空的，那么result为null.
	 */
	void handle(MethodInvocation methodInvocation, Object result);
}
