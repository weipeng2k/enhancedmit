/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.murdock.tools.enhancedmit.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import com.murdock.tools.enhancedmit.MethodInvocationManager;

/**
 * <pre>
 * 为了提供各个子Spring容器能够进行方法增强，可以使用该BeanPostProcessor来完成本容器内的增强工作，仅需要
 * 配置即可。
 * 
 * 一般来说，在web应用的多spring层级容器中会使用它。
 * 
 * 因为多个Spring容器的层级结构会是这样的
 * Parent ---1----------*----Child
 * 如果这个需要增强的bean在Child中，那么需要对这些bean进行BeanPostProcessor处理，可以这个处理器只能处理所在容器的
 * bean，因此需要将其下派到各个需要的容器中，也就是形成每个需要的Spring容器中均有MethodInvocationEnhancementBeanPostProcessor
 * 而这个Processor使用的MethodInvocationManager在Parent容器中。
 * 也就是Child可以使用Parent中的bean，但是反之却不成立。
 * 
 * </pre>
 * 
 * @author weipeng 2012-11-15 下午3:39:27
 */
public class MethodInvocationEnhancementBeanPostProcessor implements BeanPostProcessor {

    /**
     * 方法调用管理
     */
    private MethodInvocationManager methodInvocationManager;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        methodInvocationManager.analysisAndCreateEnhancement(bean);

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    // --------------------Getters and Setters----------------------//
    public void setMethodInvocationManager(MethodInvocationManager methodInvocationManager) {
        this.methodInvocationManager = methodInvocationManager;
    }

    // --------------------Getters and Setters----------------------//

}
