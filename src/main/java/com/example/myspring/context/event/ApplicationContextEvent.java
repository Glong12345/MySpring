package com.example.myspring.context.event;

import com.example.myspring.context.ApplicationContext;
import com.example.myspring.context.ApplicationEvent;

/**
 * Base class for events raised for an <code>ApplicationContext</code>.
 *
 * 应用上下文事件基类
 */
public class ApplicationContextEvent extends ApplicationEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public ApplicationContextEvent(Object source) {
        super(source);
    }

    /**
     * Get the <code>ApplicationContext</code> that the event was raised for.
     */
    public final ApplicationContext getApplicationContext() {
        return (ApplicationContext) getSource();
    }
}
