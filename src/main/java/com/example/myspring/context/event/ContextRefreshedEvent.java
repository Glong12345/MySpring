package com.example.myspring.context.event;


/**
 * Event raised when an <code>ApplicationContext</code> gets initialized or refreshed.
 *
 * 定义一个上下文刷新事件
 */
public class ContextRefreshedEvent extends ApplicationContextEvent{
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public ContextRefreshedEvent(Object source) {
        super(source);
    }
}
