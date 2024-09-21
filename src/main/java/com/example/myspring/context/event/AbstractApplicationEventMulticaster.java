package com.example.myspring.context.event;

import com.example.myspring.beans.BeansException;
import com.example.myspring.beans.factory.BeanFactory;
import com.example.myspring.beans.factory.BeanFactoryAware;
import com.example.myspring.context.ApplicationEvent;
import com.example.myspring.context.ApplicationListener;
import com.example.myspring.util.ClassUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Abstract implementation of the {@link ApplicationEventMulticaster} interface,
 * providing the basic listener registration facility.
 * <p>
 * 应用事件广播器抽象类--用于实现基本的通用的监听器注册功能
 */
public abstract class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster, BeanFactoryAware {

    /**
     * 定义一个集合用于存放监听器
     */
    public final Set<ApplicationListener<ApplicationEvent>> applicationListeners = new LinkedHashSet<>();

    /**
     * 定义一个BeanFactory，用于获取监听器
     */
    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }


    /**
     *
     * @param listener the listener to add
     *
     * 添加监听器，用于接收事件通知
     */
    @Override
    public void addApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.add((ApplicationListener<ApplicationEvent>) listener);
    }


    @Override
    public void removeApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.remove(listener);
    }


    /**
     *  返回所有对某件事件感兴趣的监听器
     */
    protected Collection<ApplicationListener> getApplicationListeners(ApplicationEvent event) {
        LinkedList<ApplicationListener> allListener = new LinkedList<>();

        // 遍历所有的监听器,找到对当前事件感兴趣的监听器
        for (ApplicationListener<ApplicationEvent> listener : applicationListeners) {
            if (supportsEvent(listener, event)){
                allListener.add(listener);
            }
        }
        return allListener;
    }

    /**
     * 判断某个监听器是否对该事件感兴趣
     */
    protected boolean supportsEvent(ApplicationListener<ApplicationEvent> applicationListener,ApplicationEvent event){
        Class<? extends ApplicationListener> listenerClass = applicationListener.getClass();

        // 按照 CglibSubclassingInstantiationStrategy、SimpleInstantiationStrategy 不同的实例化类型，需要判断后获取目标 class
        Class<?> targetClass = ClassUtils.isCglibProxyClass(listenerClass) ? listenerClass.getSuperclass() : listenerClass;
        Type genericInterface = targetClass.getGenericInterfaces()[0];

        Type actualTypeArgument = ((ParameterizedType) genericInterface).getActualTypeArguments()[0];
        String className = actualTypeArgument.getTypeName();
        Class<?> eventClassName;
        try {
            eventClassName = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new BeansException("wrong event class name: " + className);
        }
        // 判定此 eventClassName 对象所表示的类或接口与指定的 event.getClass() 参数所表示的类或接口是否相同，或是否是其超类或超接口。
        // isAssignableFrom是用来判断子类和父类的关系的，或者接口的实现类和接口的关系的，默认所有的类的终极父类都是Object。如果A.isAssignableFrom(B)结果是true，证明B可以转换成为A,也就是A可以由B转换而来。
        return eventClassName.isAssignableFrom(event.getClass());


    }

}
