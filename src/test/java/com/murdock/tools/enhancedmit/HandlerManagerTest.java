/**
 * 
 */
package com.murdock.tools.enhancedmit;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.murdock.tools.enhancedmit.domain.Handler;
import com.murdock.tools.enhancedmit.domain.extension.AfterExecutionHandler;
import com.murdock.tools.enhancedmit.domain.extension.BeforeExecutionHandler;
import com.murdock.tools.enhancedmit.domain.extension.ExceptionThrownHandler;
import com.murdock.tools.enhancedmit.enums.CapabilityTypeEnum;

/**
 * @author weipeng
 * 
 */
@ContextConfiguration(locations = { "classpath:handlerManager.xml" })
public class HandlerManagerTest extends AbstractJUnit4SpringContextTests {
	@Autowired
	private HandlerManager handlerManager;

	@Test
	public void create() {

	}

	@Test
	public void getHandler() {
		TestCase.assertNull(handlerManager.getHandler(
				CapabilityTypeEnum.SYSTEM_PRINT, ""));

		System.out.println(handlerManager.getHandler(
				CapabilityTypeEnum.SYSTEM_PRINT,
				"systemPrintAfterExecutionHandler"));

		TestCase.assertNotNull(handlerManager.getHandler(
				CapabilityTypeEnum.SYSTEM_PRINT,
				"systemPrintAfterExecutionHandler"));
	}

	@Test
	public void getDefaultHandler() {
		TestCase.assertNotNull(handlerManager.getDefaultHandlerOfType(
				CapabilityTypeEnum.SYSTEM_PRINT, Handler.class));

		System.out.println(handlerManager.getDefaultHandlerOfType(
				CapabilityTypeEnum.SYSTEM_PRINT, Handler.class));

		TestCase.assertNotNull(handlerManager.getDefaultHandlerOfType(
				CapabilityTypeEnum.SYSTEM_PRINT, AfterExecutionHandler.class));

		System.out.println(handlerManager.getDefaultHandlerOfType(
				CapabilityTypeEnum.SYSTEM_PRINT, AfterExecutionHandler.class));

		TestCase.assertNotNull(handlerManager.getDefaultHandlerOfType(
				CapabilityTypeEnum.SYSTEM_PRINT, BeforeExecutionHandler.class));

		System.out.println(handlerManager.getDefaultHandlerOfType(
				CapabilityTypeEnum.SYSTEM_PRINT, BeforeExecutionHandler.class));

		TestCase.assertNotNull(handlerManager.getDefaultHandlerOfType(
				CapabilityTypeEnum.SYSTEM_PRINT, ExceptionThrownHandler.class));

		System.out.println(handlerManager.getDefaultHandlerOfType(
				CapabilityTypeEnum.SYSTEM_PRINT, ExceptionThrownHandler.class));
	}

}
