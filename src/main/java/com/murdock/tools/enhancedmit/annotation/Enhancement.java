/**
 * 
 */
package com.murdock.tools.enhancedmit.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * 这是一个增强的注解，用于表示一个方法，它被加以增强。
 * 
 * 比如一个方法 a()
 * 我们可以对方法a进行增强，在执行之前做点事情，或者执行之后做点事情。这就表示这个方法被增强了，好比这个方法
 * 获得了多种能力，而这些能力Capability就是具体的体现。
 * 
 * </pre>
 * 
 * @author weipeng
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Enhancement {
	/**
	 * 增强的能力列表
	 * 
	 * @return
	 */
	Capability[] value();
}
