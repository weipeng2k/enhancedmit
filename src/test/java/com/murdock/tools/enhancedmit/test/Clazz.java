/**
 * 
 */
package com.murdock.tools.enhancedmit.test;

import com.murdock.tools.enhancedmit.annotation.Capability;
import com.murdock.tools.enhancedmit.annotation.EnhancedImplementation;
import com.murdock.tools.enhancedmit.annotation.Enhancement;
import com.murdock.tools.enhancedmit.enums.CapabilityTypeEnum;

/**
 * @author weipeng
 */
@EnhancedImplementation
public class Clazz {

    @Enhancement({ @Capability(type = CapabilityTypeEnum.SYSTEM_PRINT) })
    public void noreturn() {
        System.out.println("NoReturn...");
    }

    @Enhancement({ @Capability(type = CapabilityTypeEnum.SYSTEM_PRINT),
            @Capability(type = CapabilityTypeEnum.INVOCATION_COST_TIME) })
    public String returN(String param) {
        return "return... & param is " + param;
    }
    
    public String returN(int i) {
    	return null;
    }

    @Enhancement({ @Capability(type = CapabilityTypeEnum.INVOCATION_STATS) })
    public void exception() throws Exception {
        throw new Exception();
    }

    @Enhancement({ @Capability(type = CapabilityTypeEnum.INVOCATION_COST_TIME, beforeExecutionHandlerName = "34"),
            @Capability(type = CapabilityTypeEnum.NONE) })
    public String withoutEnhancement() {
        return "withoutEnhancement";
    }

    @Enhancement({ @Capability(type = CapabilityTypeEnum.SYSTEM_PRINT),
            @Capability(type = CapabilityTypeEnum.INVOCATION_COST_TIME) })
    private void pri() {

    }
}
