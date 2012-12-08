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
 * 用来注解一个Handler的实现，主要目的是能够区分容器中的Handler，便于Handler的构造和理解。
 * 
 * 一个Handler需要定义三个元素：
 * 1. Handler的名称，这样使用Capability用来标注一个Enhancement时，可以选择使用的名称；
 * 2. Handler所属的CapabilityTypeEnum，用来描述Handler属于何种Capability；
 * 3. Handler是否是默认的，默认属性没有打开，如果确认一个Handler是Capability下的默认处理器，那么可以选择
 * 使用true，但是一种能力Capability下，在BeforeExeuctionHandler、AfterExecutionHandler和ExceptionThrownHandler
 * 维度上各自仅有一个默认的Handler。
 * 
 * </pre>
 * 
 * @author weipeng
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface HandlerConfig {
	/**
	 * <pre>
	 * Handler的名称，每个Handler的名称不能相同 
	 * 
	 * 推荐命名的方式：
	 * capability + [BeforeExecutionHandler | AfterExecutionHandler | ExceptionThrownHandler]
	 * 
	 *</pre>
	 * 
	 * @return Handler的名称
	 */
	String name();

	/**
	 * <pre>
	 * 当前的Handler属于哪一种能力的类型
	 * 
	 * </pre>
	 * 
	 * @return 属于哪一种能力的类型
	 */
	CapabilityTypeEnum belongsTo();

	/**
	 * <pre>
	 * 是否是默认的处理器，当然，这个默认是针对belongsTo这个类型的
	 * 
	 * </pre>
	 * 
	 * @return 是否默认
	 */
	boolean isDefault() default false;
}
