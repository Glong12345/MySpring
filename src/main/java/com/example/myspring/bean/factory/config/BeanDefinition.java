package com.example.myspring.bean.factory.config;

/**
 * BeanDefinition，用于定义 Bean 实例化信息，最开始的实现是以一个 Object 存放对象
 * BeanDefinition 定义bean的类信息
 */
public class BeanDefinition {

    /**
     * 从Object换成了Class，即控制反转的思想，将创建对象的权利交给Spring容器，而不是由调用者创建
     */
    private Class beanClass;

    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }


}
