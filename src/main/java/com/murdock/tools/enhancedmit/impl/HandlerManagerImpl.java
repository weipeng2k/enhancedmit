/**
 * 
 */
package com.murdock.tools.enhancedmit.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;

import com.murdock.tools.enhancedmit.HandlerManager;
import com.murdock.tools.enhancedmit.annotation.HandlerConfig;
import com.murdock.tools.enhancedmit.domain.Handler;
import com.murdock.tools.enhancedmit.enums.CapabilityTypeEnum;

/**
 * <pre>
 * HandlerManager的实现，在容器初始化时将所有的Handler初始化，外界根据所需获取即可。
 * 
 * </pre>
 * 
 * @author weipeng
 */
public class HandlerManagerImpl implements HandlerManager, ApplicationContextAware, InitializingBean {

    /**
     * Spring IOC Container
     */
    private ApplicationContext                                 applicationContext;
    /**
     * 处理器集合
     */
    private final Map<CapabilityTypeEnum, List<HandlerHolder>> cachedHandlers = new HashMap<CapabilityTypeEnum, List<HandlerHolder>>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @SuppressWarnings("unchecked")
    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, Handler> handlerMap = applicationContext.getBeansOfType(Handler.class);

        if (handlerMap != null && !handlerMap.isEmpty()) {
            for (Map.Entry<String, Handler> entry : handlerMap.entrySet()) {
                addHandler(entry.getValue());
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see com.murdock.tools.enhancedmit.HandlerManager#getHandler(com.murdock.tools
     * .enhancedmit.enums.CapabilityTypeEnum, java.lang.String)
     */
    @Override
    public Handler getHandler(CapabilityTypeEnum capability, String name) {
        Handler result = null;

        if (capability != null && name != null) {
            List<HandlerHolder> handlers = cachedHandlers.get(capability);

            if (handlers != null && !handlers.isEmpty()) {
                for (HandlerHolder holder : handlers) {
                    if (holder.name.equals(name)) {
                        result = holder.handler;
                        break;
                    }
                }
            }
        }

        return result;
    }

    @Override
    public Handler getDefaultHandlerOfType(CapabilityTypeEnum capability, Class<? extends Handler> clazz) {
        Handler result = null;

        if (capability != null && clazz != null) {
            List<HandlerHolder> handlers = cachedHandlers.get(capability);

            if (handlers != null && !handlers.isEmpty()) {
                for (HandlerHolder holder : handlers) {
                    // 如果是默认且是指定的clazz的实例
                    if (holder.isDefault && clazz.isInstance(holder.handler)) {
                        result = holder.handler;
                        break;
                    }
                }
            }
        }

        return result;
    }

    /**
     * <pre>
     * 添加一个Handler到容器中，以Capability作为KEY。
     * 
     * </pre>
     * 
     * @param handler
     */
    private void addHandler(Handler handler) {
        HandlerConfig handlerConfig = handler.getClass().getAnnotation(HandlerConfig.class);
        Assert.notNull(handler);

        String name = handlerConfig.name();
        Assert.notNull(name);

        CapabilityTypeEnum type = handlerConfig.belongsTo();
        Assert.notNull(type);

        boolean isDefault = handlerConfig.isDefault();

        List<HandlerHolder> holders = cachedHandlers.get(type);

        // 如果列表为空，初始化一个
        if (holders == null) {
            holders = new ArrayList<HandlerHolder>();
            cachedHandlers.put(type, holders);
        }

        HandlerHolder holder = new HandlerHolder();
        holder.handler = handler;
        holder.isDefault = isDefault;
        holder.name = name;

        holders.add(holder);
    }

    /**
     * 承接Handler的包装类
     * 
     * @author weipeng
     */
    static final class HandlerHolder {

        /**
         * Handler
         */
        Handler handler;
        /**
         * Handler名称
         */
        String  name;
        /**
         * 是否默认
         */
        boolean isDefault;
    }
}
