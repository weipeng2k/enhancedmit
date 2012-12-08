/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.murdock.tools.invocationstats;

import java.util.Arrays;

/**
 * <pre>
 * 方法调用统计信息
 * 
 * </pre>
 * 
 * @author weipeng 2012-11-1 下午8:09:48
 */
public class InvocationStatsInfo {

    /**
     * 方法名称
     */
    private String     methodName;
    /**
     * 参数的类型数组
     */
    private Class<?>[] parameterTypes;
    /**
     * 调用的次数
     */
    private long       invocationTimes;
    /**
     * 成功的次数
     */
    private long       successTimes;
    /**
     * 失败的次数
     */
    private long       failedTimes;
    /**
     * 异常的次数
     */
    private long       exceptionTimes;
    /**
     * 平均的耗时
     */
    private long       averageMillis;
    /**
     * 额外的统计
     */
    private long       extraCounts;

    // -------------------Getters and Setters-------------------//

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public long getInvocationTimes() {
        return invocationTimes;
    }

    public void setInvocationTimes(long invocationTimes) {
        this.invocationTimes = invocationTimes;
    }

    public long getSuccessTimes() {
        return successTimes;
    }

    public void setSuccessTimes(long successTimes) {
        this.successTimes = successTimes;
    }

    public long getFailedTimes() {
        return failedTimes;
    }

    public void setFailedTimes(long failedTimes) {
        this.failedTimes = failedTimes;
    }

    public long getExceptionTimes() {
        return exceptionTimes;
    }

    public void setExceptionTimes(long exceptionTimes) {
        this.exceptionTimes = exceptionTimes;
    }

    public long getAverageMillis() {
        return averageMillis;
    }

    public void setAverageMillis(long averageMillis) {
        this.averageMillis = averageMillis;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public long getExtraCounts() {
        return extraCounts;
    }

    public void setExtraCounts(long extraCounts) {
        this.extraCounts = extraCounts;
    }

    // -------------------Getters and Setters-------------------//

    @Override
    public String toString() {
        return "InvocationStatsInfo [methodName=" + methodName + ", parameterTypes=" + Arrays.toString(parameterTypes)
               + ", invocationTimes=" + invocationTimes + ", successTimes=" + successTimes + ", failedTimes="
               + failedTimes + ", exceptionTimes=" + exceptionTimes + ", averageMillis=" + averageMillis
               + ", extraCounts=" + extraCounts + "]";
    }

}
