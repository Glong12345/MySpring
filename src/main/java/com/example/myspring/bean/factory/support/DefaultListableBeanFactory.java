package com.example.myspring.bean.factory.support;

import com.example.myspring.bean.BeansException;
import com.example.myspring.bean.factory.config.BeanDefinition;

import java.util.HashMap;
import java.util.Map;

/**
 * 该类实现注册Bean定义与获取Bean定义就可以同时使用了
 * 接口定义了注册，抽象类定义了获取
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry{

    //beanDefinitionMap用来保存BeanDefinition
    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    // 根据beanName获取BeanDefinition
    @Override
    protected BeanDefinition getBeanDefinition(String beanName) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null) {
            throw new BeansException("No bean named '" + beanName + "' is defined");
        }
        return beanDefinition;
    }

    // 将BeanDefinition注册到beanDefinitionMap中
    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }
}
