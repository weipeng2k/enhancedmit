/**
 * 
 */
package com.murdock.tools.enhancedmit.domain;

import java.util.ArrayList;
import java.util.List;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.murdock.tools.enhancedmit.domain.extension.AfterExecutionHandler;
import com.murdock.tools.enhancedmit.domain.extension.BeforeExecutionHandler;
import com.murdock.tools.enhancedmit.domain.extension.ExceptionThrownHandler;
import com.murdock.tools.enhancedmit.enums.CapabilityTypeEnum;

/**
 * <pre>
 * 方法调用增强
 * 
 * 一个方法的增强，描述了这个方法的：
 * 1. 前置调用前增强列表；
 * 2. 调用完成后增强列表；
 * 3. 异常生成时增强列表。
 * 
 * 根据对应的方法ID来对应一个增强，这里提到的方法ID不是一个方法的物理本体而是逻辑概念。
 * 
 * 增强能够执行方法调用前、方法调用后和异常抛出时的相关操作。
 * 
 * </pre>
 * 
 * @author weipeng
 */
public class MethodInvocationEnhancement {

    private static final int DEFAULT_HANDLERS_SIZE = CapabilityTypeEnum
	    .values().length;
    private static Log log = LogFactory.getLog(Handler.class);
    /**
     * 方法的ID，这里用该属性来标识一个方法调用增强
     */
    private String id;
    /**
     * 前置执行处理器
     */
    private List<BeforeExecutionHandler> beforeExecutionHandlers;
    /**
     * 后置执行处理器
     */
    private List<AfterExecutionHandler> afterExecutionHandlers;
    /**
     * 异常处理器
     */
    private List<ExceptionThrownHandler> exceptionThrownHandlers;

    /**
     * @param handler
     */
    public final void registerHandler(Handler handler) {
	if (handler != null) {
	    if (handler instanceof BeforeExecutionHandler) {
		if (beforeExecutionHandlers == null) {
		    beforeExecutionHandlers = new ArrayList<BeforeExecutionHandler>(
			    DEFAULT_HANDLERS_SIZE);
		}
		beforeExecutionHandlers.add((BeforeExecutionHandler) handler);
	    } else if (handler instanceof AfterExecutionHandler) {
		if (afterExecutionHandlers == null) {
		    afterExecutionHandlers = new ArrayList<AfterExecutionHandler>(
			    DEFAULT_HANDLERS_SIZE);
		}
		afterExecutionHandlers.add((AfterExecutionHandler) handler);
	    } else if (handler instanceof ExceptionThrownHandler) {
		if (exceptionThrownHandlers == null) {
		    exceptionThrownHandlers = new ArrayList<ExceptionThrownHandler>(
			    DEFAULT_HANDLERS_SIZE);
		}
		exceptionThrownHandlers.add((ExceptionThrownHandler) handler);
	    }
	}
    }

    /**
     * <pre>
     * 方法执行前进行执行
     * 
     * </pre>
     * 
     * @param methodInvocation
     */
    public void beforeExecution(MethodInvocation methodInvocation) {
	if (beforeExecutionHandlers != null) {
	    for (BeforeExecutionHandler beh : beforeExecutionHandlers) {
		try {
		    beh.handle(methodInvocation);
		} catch (Exception ex) {
		    // Ignore proceed, just log it.
		    log.error("handler execution got exception.", ex);
		}
	    }
	}
    }

    @Override
    public String toString() {
	return "MethodInvocationEnhancement [beforeExecutionHandlers="
		+ beforeExecutionHandlers + ", afterExecutionHandlers="
		+ afterExecutionHandlers + ", exceptionThrownHandlers="
		+ exceptionThrownHandlers + "]";
    }
}
