/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.murdock.tools.enhancedmit.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * 用于标注一个实现的类，这个类将会被解析，从而完成增强的初始化工作。
 * 
 * 这种方式是一种妥协，主要针对aop参数的实现有可能是一个代理。
 * 
 * </pre>
 * 
 * @author weipeng 2012-11-5 下午7:39:36
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface EnhancedImplementation {

}
