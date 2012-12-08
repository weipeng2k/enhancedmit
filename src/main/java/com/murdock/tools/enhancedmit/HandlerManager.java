/**
 * 
 */
package com.murdock.tools.enhancedmit;

import com.murdock.tools.enhancedmit.domain.Handler;
import com.murdock.tools.enhancedmit.enums.CapabilityTypeEnum;

/**
 * <pre>
 * 用于管理整个进程中的Handler，也就是该实现负责对Handler进行初始化，而接口本身
 * 只是描述如何从管理接口中获取Handler。
 * 
 * 比如：根据能力类型和名称获取一个Handler，或者指定一个Handler的类型来获取一个能力的默认Handler。
 * 
 * </pre>
 * 
 * @author weipeng
 * 
 */
public interface HandlerManager {

	/**
	 * <pre>
	 * 根据能力类型和名称从容器中获取一个Handler
	 * 
	 * </pre>
	 * 
	 * 
	 * @param capability
	 * @param name
	 * @return 如果没有获取到，返回空
	 */
	Handler getHandler(CapabilityTypeEnum capability, String name);
	
	/**
	 * <pre>
	 * 根据能力类型以及Handler的类型来获取默认的Handler
	 * 
	 * </pre>
	 * 
	 * @param capability
	 * @param clazz
	 * @return
	 */
	Handler getDefaultHandlerOfType(CapabilityTypeEnum capability, Class<? extends Handler> clazz);
}
