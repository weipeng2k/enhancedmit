/**
 * 
 */
package com.murdock.tools.enhancedmit.impl;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import com.murdock.tools.enhancedmit.util.MethodNameUtils;

/**
 * <pre>
 * 方法调用增强的实现，主要是获取方法的增强
 * 
 * 这里是重构的入口，简单的说就是有方法ID列表的概念
 * 
 * 
 * Method *-------1 MethodID *---------1 MethodInvocationEnhancement
 * 多个方法可以共享一个方法Id，说明这里是逻辑概念；
 * 一个方法Id可以找到一个增强；
 * 而这个增强可能应对多个方法ID。
 * 
 * 而MethodID形成一座桥链接方法的其增强，提供了足够的弹性。
 * 
 * </pre>
 * 
 * @author weipeng
 */
public class MethodInvocationManagerImpl implements MethodInvocationManager {

    private static Log                                                     log                = LogFactory.getLog(Handler.class);
    /**
     * Handler管理
     */
    private HandlerManager                                                 handlerManager;
    /**
     * 方法到方法ID
     */
    private ConcurrentHashMap<Method, String>                              method2Id          = new ConcurrentHashMap<Method, String>();
    /**
     * 方法ID到增强
     */
    private ConcurrentHashMap<String, MethodInvocationEnhancement>         id2Enhancement     = new ConcurrentHashMap<String, MethodInvocationEnhancement>();

    /*
     * (non-Javadoc)
     * @see com.murdock.tools.enhancedmit.MethodInvocationManager#fetchEnhancement (java.lang.reflect.Method)
     */
    @Override
    public MethodInvocationEnhancement fetchEnhancement(Method method) {
        if (method != null) {
            String methodId = method2Id.get(method);

            if (methodId != null) {
                return id2Enhancement.get(methodId);
            }
        }

        return null;
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
     * LOG时，将方法全部输出，可以看到增强的方法列表
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
                if (log.isInfoEnabled()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("[Enhanced] method======> ").append(method).append(" [Begin]");
                    log.info(sb.toString());
                }

                methodInvocationEnhancement = new MethodInvocationEnhancement();

                // 如果当前注解是默认的话，使用默认ID生成模式
                if (Enhancement.DEFAULT_METHOD_ID.equals(enhancement.id())) {
                    methodInvocationEnhancement.setId(MethodNameUtils.getMethodSignature(method.getName(),
                                                                                         method.getParameterTypes()));
                } else {
                    methodInvocationEnhancement.setId(enhancement.id());
                }

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

                if (log.isInfoEnabled()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("[Enhanced] method======> ").append(method).append(" [Done]");
                    log.info(sb.toString());
                }
            }
        }

        return methodInvocationEnhancement;
    }

    /*
     * (non-Javadoc)
     * @see com.murdock.tools.enhancedmit.MethodInvocationManager#analysisAndCreateEnhancement(java.lang.Object)
     */
    @Override
    public void analysisAndCreateEnhancement(Object bean) {
        if (bean != null) {
            Class<?> clazz = bean.getClass();
            // Mark了增强实现标记，解析
            if (clazz.isAnnotationPresent(EnhancedImplementation.class)) {
                Method[] methods = clazz.getMethods();
                if (methods != null) {
                    for (Method method : methods) {
                        MethodInvocationEnhancement enhancement = generateEnhancement(method);
                        if (enhancement != null) {
                            method2Id.put(method, enhancement.getId());
                            id2Enhancement.put(enhancement.getId(), enhancement);
                        }
                    }
                }
            }
        }

    }

    // --------------------Getters and Setters----------------------//
    public void setHandlerManager(HandlerManager handlerManager) {
        this.handlerManager = handlerManager;
    }
    // --------------------Getters and Setters----------------------//

}
