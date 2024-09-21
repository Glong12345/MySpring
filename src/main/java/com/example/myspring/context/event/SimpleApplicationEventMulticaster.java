package com.example.myspring.context.event;

import com.example.myspring.beans.factory.BeanFactory;
import com.example.myspring.context.ApplicationEvent;

/**
 * Simple implementation of the {@link ApplicationEventMulticaster} interface.
 *
 * 应用程序事件广播器的简单实现
 */
public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster{

    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }

    /**
     *
     * @param event the event to multicast
     *
     * 通知所有监听器事件
     */
    @SuppressWarnings("unchecked")
    @Override
    public void multicastEvent(final ApplicationEvent event) {
        getApplicationListeners(event).forEach(listener -> listener.onApplicationEvent(event));
    }
}
