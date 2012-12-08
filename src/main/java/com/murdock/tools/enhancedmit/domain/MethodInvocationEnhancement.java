/**
 * 
 */
package com.murdock.tools.enhancedmit.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.murdock.tools.enhancedmit.domain.extension.AfterExecutionHandler;
import com.murdock.tools.enhancedmit.domain.extension.BeforeExecutionHandler;
import com.murdock.tools.enhancedmit.domain.extension.ExceptionThrownHandler;
import com.murdock.tools.enhancedmit.enums.CapabilityTypeEnum;

/**
 * <pre>
 * 方法调用增强，一个方法对应一个增强
 * 
 * 一个方法的增强，描述了这个方法的：
 * 1. 前置调用前增强列表；
 * 2. 调用完成后增强列表；
 * 3. 异常生成时增强列表。
 * 
 * </pre>
 * 
 * @author weipeng
 */
public final class MethodInvocationEnhancement {

    private static final int             DEFAULT_HANDLERS_SIZE = CapabilityTypeEnum.values().length;
    /**
     * 前置执行处理器
     */
    private List<BeforeExecutionHandler> beforeExecutionHandlers;
    /**
     * 后置执行处理器
     */
    private List<AfterExecutionHandler>  afterExecutionHandlers;
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
                    beforeExecutionHandlers = new ArrayList<BeforeExecutionHandler>(DEFAULT_HANDLERS_SIZE);
                }
                beforeExecutionHandlers.add((BeforeExecutionHandler) handler);
            } else if (handler instanceof AfterExecutionHandler) {
                if (afterExecutionHandlers == null) {
                    afterExecutionHandlers = new ArrayList<AfterExecutionHandler>(DEFAULT_HANDLERS_SIZE);
                }
                afterExecutionHandlers.add((AfterExecutionHandler) handler);
            } else if (handler instanceof ExceptionThrownHandler) {
                if (exceptionThrownHandlers == null) {
                    exceptionThrownHandlers = new ArrayList<ExceptionThrownHandler>(DEFAULT_HANDLERS_SIZE);
                }
                exceptionThrownHandlers.add((ExceptionThrownHandler) handler);
            }
        }
    }

    public final List<BeforeExecutionHandler> getBeforeExecutionHandlers() {
        if (beforeExecutionHandlers == null) {
            return Collections.emptyList();
        }

        return beforeExecutionHandlers;
    }

    public final List<AfterExecutionHandler> getAfterExecutionHandlers() {
        if (afterExecutionHandlers == null) {
            return Collections.emptyList();
        }

        return afterExecutionHandlers;
    }

    public final List<ExceptionThrownHandler> getExceptionThrownHandlers() {
        if (exceptionThrownHandlers == null) {
            return Collections.emptyList();
        }

        return exceptionThrownHandlers;
    }

    @Override
    public String toString() {
        return "MethodInvocationEnhancement [beforeExecutionHandlers=" + beforeExecutionHandlers
               + ", afterExecutionHandlers=" + afterExecutionHandlers + ", exceptionThrownHandlers="
               + exceptionThrownHandlers + "]";
    }
}
