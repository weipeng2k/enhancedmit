/**
 * 
 */
package com.murdock.tools.enhancedmit.impl;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.Assert;

import com.murdock.tools.enhancedmit.HandlerManager;
import com.murdock.tools.enhancedmit.MethodInvocationManager;
import com.murdock.tools.enhancedmit.annotation.Capability;
import com.murdock.tools.enhancedmit.annotation.EnhancedImplementation;
import com.murdock.tools.enhancedmit.annotation.Enhancement;
import com.murdock.tools.enhancedmit.domain.Handler;
import com.murdock.tools.enhancedmit.domain.MethodInvocationEnhancement;
import com.murdock.tools.enhancedmit.domain.extension.AfterExecutionHandler;
import com.murdock.tools.enhancedmit.domain.extension.BeforeExecutionHandler;
import com.murdock.tools.enhancedmit.domain.extension.ExceptionThrownHandler;
import com.murdock.tools.enhancedmit.enums.CapabilityTypeEnum;

/**
 * <pre>
 * 方法调用增强的实现，主要是获取方法的增强
 * 
 * </pre>
 * 
 * @author weipeng
 */
public class MethodInvocationManagerImpl implements MethodInvocationManager, BeanPostProcessor {

    private static Log                                                     log                = LogFactory.getLog(MethodInvocationManager.class);
    /**
     * Handler管理
     */
    private HandlerManager                                                 handlerManager;
    /**
     * 缓存增强
     */
    private ConcurrentHashMap<MethodIdentity, MethodInvocationEnhancement> cachedEnhancements = new ConcurrentHashMap<MethodIdentity, MethodInvocationEnhancement>();

    /*
     * (non-Javadoc)
     * @see com.murdock.tools.enhancedmit.MethodInvocationManager#beforeExecution
     * (org.aopalliance.intercept.MethodInvocation, com.murdock.tools.enhancedmit.domain.MethodInvocationEnhancement)
     */
    @Override
    public void beforeExecution(MethodInvocation methodInvocation, MethodInvocationEnhancement enhancement) {
        if (enhancement != null) {
            for (BeforeExecutionHandler handler : enhancement.getBeforeExecutionHandlers()) {
                try {
                    handler.handle(methodInvocation);
                } catch (Exception ex) {
                    // Ignore proceed, just log it.
                    log.error("handler execution got exception.", ex);
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see com.murdock.tools.enhancedmit.MethodInvocationManager#afterExecution(
     * org.aopalliance.intercept.MethodInvocation, java.lang.Object,
     * com.murdock.tools.enhancedmit.domain.MethodInvocationEnhancement)
     */
    @Override
    public void afterExecution(MethodInvocation methodInvocation, Object result, MethodInvocationEnhancement enhancement) {
        if (enhancement != null) {
            for (AfterExecutionHandler handler : enhancement.getAfterExecutionHandlers()) {
                try {
                    handler.handle(methodInvocation, result);
                } catch (Exception ex) {
                    // Ignore proceed, just log it.
                    log.error("handler execution got exception.", ex);
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see com.murdock.tools.enhancedmit.MethodInvocationManager#exceptionThrown
     * (org.aopalliance.intercept.MethodInvocation, java.lang.Throwable,
     * com.murdock.tools.enhancedmit.domain.MethodInvocationEnhancement)
     */
    @Override
    public void exceptionThrown(MethodInvocation methodInvocation, Throwable exception,
                                MethodInvocationEnhancement enhancement) {

        if (enhancement != null) {
            for (ExceptionThrownHandler handler : enhancement.getExceptionThrownHandlers()) {
                try {
                    handler.handle(methodInvocation, exception);
                } catch (Exception ex) {
                    // Ignore proceed, just log it.
                    log.error("handler execution got exception.", ex);
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see com.murdock.tools.enhancedmit.MethodInvocationManager#fetchEnhancement (java.lang.reflect.Method)
     */
    @Override
    public MethodInvocationEnhancement fetchEnhancement(Method method) {
        MethodInvocationEnhancement methodInvocationEnhancement = null;
        MethodIdentity mi = encodeMethod(method);
        if (mi != null) {
            methodInvocationEnhancement = cachedEnhancements.get(mi);
        }

        return methodInvocationEnhancement;
    }

    /**
     * <pre>
     * 如果BeanName是DEFAULT，那么选用枚举所标注的默认handler；
     * 如果BeanName是null，那么不用进行注册，当然这种情况比较少；
     * 如果BeanName不是上面的，则使用自定义的handler。
     * 
     * </pre>
     * 
     * @param methodInvocationEnhancement
     * @param capability
     * @param name
     * @param clazz
     */
    private void fetchAndRegisterHandler(MethodInvocationEnhancement methodInvocationEnhancement,
                                         CapabilityTypeEnum capability, String name, Class<? extends Handler> clazz) {
        if (methodInvocationEnhancement != null && capability != null) {

            // name为空或者空串，就不注册了
            if (name != null && !name.trim().isEmpty()) {
                // 如果是default，使用默认的handler
                if (Capability.DEFAULT_HANDLER_NAME.equals(name)) {
                    Handler handler = handlerManager.getDefaultHandlerOfType(capability, clazz);
                    methodInvocationEnhancement.registerHandler(handler);
                } else {
                    Handler handler = handlerManager.getHandler(capability, name);
                    methodInvocationEnhancement.registerHandler(handler);
                }
            }
        }
    }

    /**
     * <pre>
     * 生成增强，该方法必须是实现的方法
     * 
     * </pre>
     * 
     * @param method
     * @return
     */
    private MethodInvocationEnhancement generateEnhancement(Method method) {
        MethodInvocationEnhancement methodInvocationEnhancement = null;

        if (method != null && method.isAnnotationPresent(Enhancement.class)) {
            Enhancement enhancement = method.getAnnotation(Enhancement.class);

            // 有增强注解，分析注解
            if (enhancement != null) {
                methodInvocationEnhancement = new MethodInvocationEnhancement();
                Capability[] capabilities = enhancement.value();

                if (capabilities != null) {
                    // 分析每个增强的能力
                    // 需要查看对应的BeanName
                    for (Capability capability : capabilities) {
                        CapabilityTypeEnum type = capability.type();
                        Assert.notNull(type);
                        if (log.isDebugEnabled()) {
                            log.debug("generateEnhancement, capability type is " + type);
                        }

                        fetchAndRegisterHandler(methodInvocationEnhancement, type,
                                                capability.beforeExecutionHandlerName(), BeforeExecutionHandler.class);

                        fetchAndRegisterHandler(methodInvocationEnhancement, type,
                                                capability.afterExecutionHandlerName(), AfterExecutionHandler.class);

                        fetchAndRegisterHandler(methodInvocationEnhancement, type,
                                                capability.exceptionThrownHandlerName(), ExceptionThrownHandler.class);
                    }
                }
            }
        }

        return methodInvocationEnhancement;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.beans.factory.config.BeanPostProcessor# postProcessAfterInitialization(java.lang.Object,
     * java.lang.String)
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        return bean;
    }

    /*
     * parameterTypes (non-Javadoc)
     * @see org.springframework.beans.factory.config.BeanPostProcessor#
     * postProcessBeforeInitialization(java.lang.Object, java.lang.String)
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean != null) {
            Class<?> clazz = bean.getClass();
            // Mark了增强实现标记，解析
            if (clazz.isAnnotationPresent(EnhancedImplementation.class)) {
                Method[] methods = clazz.getMethods();
                if (methods != null) {
                    for (Method method : methods) {
                        MethodInvocationEnhancement enhancement = generateEnhancement(method);
                        if (enhancement != null) {
                            MethodIdentity mi = encodeMethod(method);
                            if (mi != null) {
                                cachedEnhancements.put(mi, enhancement);
                            }
                        }
                    }
                }
            }
        }

        return bean;
    }

    /**
     * <pre>
     * 将一个方法转义为方法标识
     * 
     * </pre>
     * 
     * @param method
     * @return
     */
    private static final MethodIdentity encodeMethod(Method method) {
        if (method != null) {
            return new MethodIdentity(method.getName(), method.getParameterTypes());
        }

        return null;
    }

    /**
     * <pre>
     * 方法标识，使用一个方法名称和参数类型来标识一个方法，外部不用知晓EhancedMIT如何表示一个方法
     * 
     * </pre>
     * 
     * @author weipeng
     */
    static final class MethodIdentity {

        /**
         * 方法名称
         */
        private String     methodName;
        /**
         * 类型列表名称
         */
        private Class<?>[] parameterTypes;

        public MethodIdentity(){

        }

        public MethodIdentity(String methodName, Class<?>[] parameterTypes){
            this.methodName = methodName;
            this.parameterTypes = parameterTypes;
        }

        // -------------------Getter and Setters------------------//

        public String getMethodName() {
            return methodName;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }

        public Class<?>[] getParameterTypes() {
            return parameterTypes;
        }

        public void setParameterTypes(Class<?>[] parameterTypes) {
            this.parameterTypes = parameterTypes;
        }

        // -------------------Getter and Setters------------------//
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((methodName == null) ? 0 : methodName.hashCode());
            result = prime * result + Arrays.hashCode(parameterTypes);
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            MethodIdentity other = (MethodIdentity) obj;
            if (methodName == null) {
                if (other.methodName != null) return false;
            } else if (!methodName.equals(other.methodName)) return false;
            if (!Arrays.equals(parameterTypes, other.parameterTypes)) return false;
            return true;
        }

    }

    // --------------------Getters and Setters----------------------//
    public void setHandlerManager(HandlerManager handlerManager) {
        this.handlerManager = handlerManager;
    }
    // --------------------Getters and Setters----------------------//

}
