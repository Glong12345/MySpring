package com.example.myspring.bean.factory.support;

import com.example.myspring.bean.factory.BeanFactory;
import com.example.myspring.bean.BeansException;
import com.example.myspring.bean.factory.config.BeanDefinition;

/**
 * 抽象类定义模板方法,定义通用的bean工厂，要实现bean工厂接口，同时要继承bean的单例注册类，便于注册和获取bean对象
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory  {

    @Override
    public Object getBean(String beanName) throws BeansException {
        // 先从单例池中获取bean对象
        Object singleton = super.getSingleton(beanName);
        if (singleton != null){
            return singleton;
        }

        // 如果单例池中没有，则创建bean对象
        // 需要拿到bean的注册信息，然后通过反射创建bean对象
        // 这里需要抽象方法，由子类实现
        BeanDefinition beanDefinition = getBeanDefinition(beanName);

        Object bean = createBean(beanName, beanDefinition);
        return bean;
    }

    /**
     * 获取bean的注册信息
     * @param beanName
     * @return
     */
    protected abstract BeanDefinition getBeanDefinition(String beanName);

    /**
     * 创建bean对象
     * @param beanName
     * @param beanDefinition
     * @return
     */
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition);
}
