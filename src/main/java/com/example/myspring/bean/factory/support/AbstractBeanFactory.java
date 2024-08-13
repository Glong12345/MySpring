package com.example.myspring.bean.factory.support;

import com.example.myspring.bean.factory.BeanFactory;
import com.example.myspring.bean.BeansException;
import com.example.myspring.bean.factory.config.BeanDefinition;

/**
 * 抽象类定义模板方法,定义通用的bean工厂，要实现bean工厂接口，同时要继承bean的单例注册类，便于注册和获取bean对象
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    // getBean方法发生了重载，抽取出公共部分，通过doGetBean方法实现
    @Override
    public Object getBean(String beanName) throws BeansException {
        // 这里相当于无参的getBean方法
        return doGetBean(beanName, null);
    }

    @Override
    public Object getBean(String beanName, Object... args) throws BeansException {
        // 这里相当于有参的getBean方法
        return doGetBean(beanName, args);
    }

    /**
     * getBean方法的落地实现，先从单例池中获取，如果单例池中没有，则创建bean对象
     * @param name
     * @param args
     * @return
     * @param <T>
     */
    protected <T> T doGetBean(final String name, final Object[] args) {
        // 先从单例池中获取bean对象
        Object bean = getSingleton(name);
        if (bean != null) {
            return (T) bean;
        }

        // 如果单例池中没有，则创建bean对象
        // 需要拿到bean的注册信息，然后通过反射创建bean对象
        // 这里需要抽象方法，由子类实现
        BeanDefinition beanDefinition = getBeanDefinition(name);
        return (T)createBean(name, beanDefinition, args);
    }

    /**
     * 获取bean的注册信息
     *
     * @param beanName
     * @return
     */
    protected abstract BeanDefinition getBeanDefinition(String beanName);

    /**
     * 创建bean对象
     *
     * @param beanName
     * @param beanDefinition
     * @return
     */
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args);
}
