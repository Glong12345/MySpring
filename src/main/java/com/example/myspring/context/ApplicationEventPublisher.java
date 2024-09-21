package com.example.myspring.context;

/**
 * Interface that encapsulates event publication functionality.
 * Serves as super-interface for ApplicationContext.
 *
 * 事件的发布接口，所有的事件都需要从这个接口发布出去
 */
public interface ApplicationEventPublisher {

    /**
     * Notify all listeners registered with this application of an application
     * event. Events may be framework events (such as RequestHandledEvent)
     * or application-specific events.
     *
     * @param event the event to publish
     *
     * 发布应用事件
     */
    void publishEvent(ApplicationEvent event);

}
