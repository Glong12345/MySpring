package com.example.myspring.bean.factory.support;

import com.example.myspring.bean.BeansException;
import com.example.myspring.bean.factory.config.BeanDefinition;

/**
 * 该类是支持自动装配的Bean工厂，主要用于通过bean的类型创建Bean实例
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory{

    /**
     * 创建Bean实例
     * @param beanName
     * @param beanDefinition
     * @return
     */
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) {
        Object bean;

        //  通过反射实例化对象
        try {
            bean = beanDefinition.getBeanClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new BeansException("Instantiation of bean failed",e);
        }

        // 将bean实例化后放入单例池缓存中
        addSingleton(beanName, bean);
        return bean;
    }

}
