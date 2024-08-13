package com.example.myspring.bean.factory.support.instantiation;

import com.example.myspring.bean.BeansException;
import com.example.myspring.bean.factory.config.BeanDefinition;
import com.example.myspring.bean.factory.support.InstantiationStrategy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * JDK实现实例化策略
 */
public class SimpleInstantiationStrategy implements InstantiationStrategy {
    @Override
    public Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args) throws BeansException {
        Class clazz = beanDefinition.getBeanClass();

        // 策略模式，根据构造器实例化对象
        try {
            if (null != ctor){
                // 根据传入的构造器的参数类型，获取对应的构造器
                return clazz.getDeclaredConstructor(ctor.getParameterTypes()).newInstance(args);
            }else {
                // 如果没有传入构造器，则使用无参构造器
                return clazz.getDeclaredConstructor().newInstance();
            }
        }catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e){
            throw new BeansException("Failed to instantiate [" + clazz.getName() + "]",e);
        }


    }
}
