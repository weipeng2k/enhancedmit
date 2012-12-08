/**
 * 
 */
package com.murdock.tools.enhancedmit.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.murdock.tools.enhancedmit.enums.CapabilityTypeEnum;

/**
 * <pre>
 * 代表增强的一种能力，比如一个方法增加打印的能力，或者方法调用统计的能力
 * 
 * </pre>
 * 
 * @author weipeng
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
@Documented
public @interface Capability {

	static final String DEFAULT_HANDLER_NAME = "default";

	/**
	 * <pre>
	 * 该Handler是描述何种增强能力的类型
	 * 
	 * </pre>
	 * 
	 * @return
	 */
	CapabilityTypeEnum type();

	/**
	 * <pre>
	 * 方法执行前的HandlerName
	 * 
	 * 如果返回null，则代表当前的能力不加入处理器
	 * 
	 * </pre>
	 * 
	 * @return
	 */
	String beforeExecutionHandlerName() default DEFAULT_HANDLER_NAME;

	/**
	 * <pre>
	 * 方法执行后的HandlerName，如果方法抛出异常，将不会执行
	 * 
	 * 如果返回null，则代表当前的能力不加入处理器
	 * 
	 * </pre>
	 * 
	 * @return
	 */
	String afterExecutionHandlerName() default DEFAULT_HANDLER_NAME;

	/**
	 * <pre>
	 * 方法执行时抛出异常执行的HandlerName
	 * 
	 * 如果返回null，则代表当前的能力不加入处理器
	 * 
	 * </pre>
	 * 
	 * @return
	 */
	String exceptionThrownHandlerName() default DEFAULT_HANDLER_NAME;
}
