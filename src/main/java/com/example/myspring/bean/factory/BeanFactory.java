package com.example.myspring.bean.factory;

import com.example.myspring.bean.BeansException;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * BeanFactory，代表了 Bean 对象的工厂，可以存放 Bean 定义到 Map 中以及获取。
 */
public interface BeanFactory {
//    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
//
//    public Object getBean(String beanName){
//        return beanDefinitionMap.get(beanName).getBean();
//    }
//
//    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition){
//        beanDefinitionMap.put(beanName, beanDefinition);
//    }

    /**
     * 将BeanFactory对象换成接口，并声明获取bean的方法
     * @param beanName
     * @return
     */
    public Object getBean(String beanName) throws BeansException;

}