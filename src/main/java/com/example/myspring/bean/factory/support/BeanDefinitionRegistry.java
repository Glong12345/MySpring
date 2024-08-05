package com.example.myspring.bean.factory.support;

import com.example.myspring.bean.factory.config.BeanDefinition;

/**
 * 该接口定义了BeanDefinition注册表，即可以用于注册bean的定义类
 */
public interface BeanDefinitionRegistry {


    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);
}
