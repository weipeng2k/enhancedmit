/**
 * 
 */
package com.murdock.tools.enhancedmit.intercepter;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.murdock.tools.enhancedmit.MethodInvocationManager;
import com.murdock.tools.enhancedmit.domain.MethodInvocationEnhancement;

/**
 * <pre>
 * 方法调用拦截的实现，使用Spring AOP
 * 
 * </pre>
 * 
 * @author weipeng
 */
public class MethodInvocationEnhancementInterceptor implements MethodInterceptor {

    /**
     * 方法调用管理
     */
    private MethodInvocationManager methodInvocationManager;

    /*
     * (non-Javadoc)
     * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept .MethodInvocation)
     */
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        // 获取当前方法的增强
        MethodInvocationEnhancement enhancement = methodInvocationManager.fetchEnhancement(methodInvocation.getMethod());

        Object result = null;
        try {
            if (enhancement != null) {
                enhancement.beforeExecution(methodInvocation);
            }
            result = methodInvocation.proceed();
            if (enhancement != null) {
                enhancement.afterExecution(methodInvocation, result);
            }
        } catch (Throwable exception) {
            if (enhancement != null) {
                enhancement.exceptionThrown(methodInvocation, exception);
            }
            throw exception;
        }

        return result;
    }

    public void setMethodInvocationManager(MethodInvocationManager methodInvocationManager) {
        this.methodInvocationManager = methodInvocationManager;
    }

}
