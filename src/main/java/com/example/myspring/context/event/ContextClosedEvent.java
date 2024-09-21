package com.example.myspring.context.event;

/**
 * Event raised when an <code>ApplicationContext</code> gets closed.
 *
 * 定义上下文关闭事件
 */
public class ContextClosedEvent extends ApplicationContextEvent{
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public ContextClosedEvent(Object source) {
        super(source);
    }
}
