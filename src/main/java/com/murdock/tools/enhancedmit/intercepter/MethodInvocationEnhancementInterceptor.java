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
    private MethodInvocationManager                                methodInvocationManager;

    /*
     * (non-Javadoc)
     * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept .MethodInvocation)
     */
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        MethodInvocationEnhancement enhancement = methodInvocationManager.fetchEnhancement(methodInvocation.getMethod());
        // MethodInvocationEnhancement enhancement = cachedEnhancements.get(calledMethod);
        // if (enhancement == null) {
        // // 获取链接点的对象
        // Object joinpoint = methodInvocation.getThis();
        // // 需要获取增强的方法，当然，如果存在joinpoint的情况下(不为static方法)
        // Method needFetchMethod = calledMethod;
        // if (joinpoint != null) {
        // needFetchMethod = joinpoint.getClass().getMethod(methodInvocation.getMethod().getName(),
        // methodInvocation.getMethod().getParameterTypes());
        // }
        // enhancement = methodInvocationManager.fetchEnhancement(needFetchMethod);
        //
        // cachedEnhancements.putIfAbsent(calledMethod, enhancement);
        // }

        Object result = null;
        try {
            if (enhancement != null) {
                methodInvocationManager.beforeExecution(methodInvocation, enhancement);
            }
            result = methodInvocation.proceed();
            if (enhancement != null) {
                methodInvocationManager.afterExecution(methodInvocation, result, enhancement);
            }
        } catch (Throwable exception) {
            if (enhancement != null) {
                methodInvocationManager.exceptionThrown(methodInvocation, exception, enhancement);
            }
            throw exception;
        }

        return result;
    }

    public void setMethodInvocationManager(MethodInvocationManager methodInvocationManager) {
        this.methodInvocationManager = methodInvocationManager;
    }

}
