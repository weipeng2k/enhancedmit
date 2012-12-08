/**
 * 
 */
package com.murdock.tools.enhancedmit.test;

import java.util.Random;

import com.murdock.tools.enhancedmit.annotation.Capability;
import com.murdock.tools.enhancedmit.annotation.EnhancedImplementation;
import com.murdock.tools.enhancedmit.annotation.Enhancement;
import com.murdock.tools.enhancedmit.enums.CapabilityTypeEnum;

/**
 * @author weipeng
 */
@EnhancedImplementation
public class InterfaceImpl implements Interface {

    /*
     * (non-Javadoc)
     * @see com.murdock.tools.enhancedmit.test.Interface#echo(java.lang.String)
     */
    @Override
    @Enhancement({ @Capability(type = CapabilityTypeEnum.INVOCATION_STATS) })
    public String echo(String param) throws ServiceException {

        Random random = new Random();
        try {
            Thread.sleep(random.nextInt(20));

            if (random.nextInt(20) % 7 == 0) {
                throw new ServiceException();
            }
            
            if (random.nextInt(20) % 9 == 0) {
                throw new RuntimeException();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return param;
    }

    @Enhancement({ @Capability(type = CapabilityTypeEnum.SYSTEM_PRINT, exceptionThrownHandlerName = ""),
        @Capability(type = CapabilityTypeEnum.INVOCATION_COST_TIME) })
    public int test(int i) {
        return i;
    }

    @Override
    @Enhancement({ @Capability(type = CapabilityTypeEnum.SYSTEM_PRINT, exceptionThrownHandlerName = ""),
            @Capability(type = CapabilityTypeEnum.INVOCATION_COST_TIME) })
    public void exception() {
        try {
            pri();
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("我是异常");
    }

    @Enhancement({ @Capability(type = CapabilityTypeEnum.SYSTEM_PRINT),
            @Capability(type = CapabilityTypeEnum.INVOCATION_COST_TIME) })
    private void pri() {

    }

}
