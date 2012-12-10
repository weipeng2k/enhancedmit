/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.murdock.tools.enhancedmit.util;

import java.lang.reflect.Method;

/**
 * <pre>
 * 方法名称工具，用来获取一个方法的签名
 * 
 * </pre>
 * 
 * @author weipeng 2012-11-6 下午12:06:52
 */
public final class MethodNameUtils {

    /**
     * <pre>
     * 获取方法的签名，组合是方法名称和类型列表
     * 
     * </pre>
     * 
     * @param methodName
     * @param parameterTypes
     * @return
     */
    public static final String getMethodSignature(String methodName, Class<?>[] parameterTypes) {
        if (methodName == null) {
            return null;
        }

        StringBuilder prefix = new StringBuilder();
        prefix.append(methodName);
        prefix.append("_");
        // 有参数，遍历参数类型，进行拼接
        if (parameterTypes != null && parameterTypes.length > 0) {
            for (Class<?> clazz : parameterTypes) {
                prefix.append(clazz.getSimpleName()).append("_");
            }
        }

        return prefix.toString();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Method[] methods = Object.class.getMethods();
        for (Method method : methods) {
            System.out.println(getMethodSignature(method.getName(), method.getParameterTypes()));
        }
    }
}
