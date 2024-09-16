package com.example.myspring.beans.factory.config;

import com.example.myspring.beans.PropertyValues;

/**
 * BeanDefinition，用于定义 Bean 实例化信息，最开始的实现是以一个 Object 存放对象
 * BeanDefinition 定义bean的类信息
 */
public class BeanDefinition {

    /**
     * 从Object换成了Class，即控制反转的思想，将创建对象的权利交给Spring容器，而不是由调用者创建
     */
    private Class beanClass;

    /**
     * 新增属性信息，用于存储bean的属性信息
     */
    private PropertyValues propertyValues;

    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
    }

    /**
     * 新增带有属性信息的构造函数
     * @param beanClass
     * @param propertyValues
     */
    public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }
}
