package com.example.myspring.context.event;


import com.example.myspring.context.ApplicationEvent;
import com.example.myspring.context.ApplicationListener;

/**
 * Interface to be implemented by objects that can manage a number of
 * {@link ApplicationListener} objects, and publish events to them.
 *
 * 定义上下文事件广播器接口，用于管理多个监听器，并发布事件到监听器
 */
public interface ApplicationEventMulticaster {

    /**
     * Add a listener to be notified of all events.
     * @param listener the listener to add
     *
     * 添加监听器，用于接收事件通知
     */
    void addApplicationListener(ApplicationListener<?> listener);

    /**
     * Remove a listener from the notification list.
     * @param listener the listener to remove
     *
     * 移除监听器
     */
    void removeApplicationListener(ApplicationListener<?> listener);

    /**
     * Multicast the given application event to appropriate listeners.
     * @param event the event to multicast
     *
     * 通知所有监听器事件
     */
    void multicastEvent(ApplicationEvent event);
}
