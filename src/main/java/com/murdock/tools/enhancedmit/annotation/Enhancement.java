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
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Enhancement {

    static final String DEFAULT_METHOD_ID = "default";

    /**
     * <pre>
     * 方法的ID
     * 
     * 注意：
     * 默认的生成规则是：方法名_参数短类名1_参数短类名2_
     * 比如
     * echo(String name)，默认的KEY为：echo_String_
     * dummy()，默认的KEY为：dummy_
     * add(int delta)，默认的KEY为：add_int_
     * 
     * </pre>
     * 
     * @return
     */
    String id() default DEFAULT_METHOD_ID;

    /**
     * 增强的能力列表
     * 
     * @return
     */
    Capability[] value();
}
