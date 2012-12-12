/**
 * 
 */
package com.murdock.tools.enhancedmit;

import java.lang.reflect.Method;

import com.murdock.tools.enhancedmit.domain.MethodInvocationEnhancement;

/**
 * <pre>
 * 方法调用管理者
 * 
 * 该接口用来关联方法调用以及增强相关的内容，主要包括：
 * 1.在方法执行前调用增强；
 * 2.在方法执行后调用增强；
 * 3.当异常抛出后执行增强。
 * 
 * </pre>
 * 
 * @author weipeng
 * 
 */
public interface MethodInvocationManager {

	/**
	 * <pre>
	 * 分析方法上的注解，一次分析，分析的主要步骤：
	 * 1.首先根据method，生成一个MethodInvocationEnhancement；
	 * 2.查询method上的注解，即@Enhancement，分析Enhancement所标注的before、after和exception的Handler；
	 * 3.将对应的Handler设置到MethodInvocationEnhancement中。
	 * 该方法的目的是依据真实的method将对应的增强生成，这里由于需要获取spring容器中的bean，所以实现层面需要耦合
	 * spring的{@link org.springframework.context.ApplicationContextAware}
	 * 
	 * </pre>
	 * 
	 * @param method
	 *            方法不能为空
	 * @return 方法增强
	 */
	MethodInvocationEnhancement fetchEnhancement(Method method);
	
	/**
     * <pre>
     * 传入一个Spring Bean，分析并进行增强的生成工作，分析的工作如下：
     * 
     * 1.首先看当前的Bean是否有EnhancedImplementation的注解标注；
     * 2.如果有的话，分析所有的方法，针对每个方法进行处理；
     * 3.首先根据method，生成一个MethodInvocationEnhancement；
     * 4.查询method上的注解，即@Enhancement，分析Enhancement所标注的before、after和exception的Handler；
     * 5.将对应的Handler设置到MethodInvocationEnhancement中。
     * 
     * 下次，在调用fetchEnhancement，将会快速返回。
     * 
     * </pre>
     * 
     * @param bean
     */
    void analysisAndCreateEnhancement(Object bean);
    
}
