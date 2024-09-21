package com.example.myspring.context;

import java.util.EventListener;


/**
 * Interface to be implemented by application event listeners.
 * Based on the standard <code>java.util.EventListener</code> interface
 * for the Observer design pattern.
 *
 * 声明一个接口，用于监听Spring容器中发布的事件
 * @param <E>
 */
public interface ApplicationListener <E extends ApplicationEvent> extends EventListener {



    /**
     * Handle an application event.
     * @param event the event to respond to
     *
     * 处理Spring容器中发布的事件
     */
    void onApplicationEvent(E event);

}
