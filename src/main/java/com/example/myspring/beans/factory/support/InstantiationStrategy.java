package com.example.myspring.beans.factory.support;


import com.example.myspring.beans.BeansException;
import com.example.myspring.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

public interface InstantiationStrategy {

    /**
     * 定义实例化策略接口
     * @param beanDefinition
     * @param beanName
     * @param ctor
     * @param args
     * @return bean实例
     * @throws BeansException
     */
    Object instantiate(BeanDefinition beanDefinition,String beanName, Constructor ctor,Object[] args) throws BeansException;
}
