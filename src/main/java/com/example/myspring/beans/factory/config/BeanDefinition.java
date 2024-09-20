package com.example.myspring.beans.factory.config;

import com.example.myspring.beans.PropertyValues;

/**
 * BeanDefinition，用于定义 Bean 实例化信息，最开始的实现是以一个 Object 存放对象
 * BeanDefinition 定义bean的类信息
 */
public class BeanDefinition {

    /**
     * 新增 scope 属性，用于定义 Bean 的作用域，单例模式、原型模式 (默认是单例)
     */
    String SCOPE_SINGLETON = ConfigurableBeanFactory.SCOPE_SINGLETON;

    String SCOPE_PROTOTYPE = ConfigurableBeanFactory.SCOPE_PROTOTYPE;

    private String scope = SCOPE_SINGLETON;

    private boolean singleton = true;

    private boolean prototype = false;

    /**
     * 从Object换成了Class，即控制反转的思想，将创建对象的权利交给Spring容器，而不是由调用者创建
     */
    private Class beanClass;

    /**
     * 新增属性信息，用于存储bean的属性信息
     */
    private PropertyValues propertyValues;

    /**
     * 新增 initMethodName 和 destroyMethodName，用于存储bean的初始化方法和销毁方法
     * 这两个属性是为了在 spring.xml 配置的 Bean 对象中，可以配置 init-method="initDataMethod" destroy-method="destroyDataMethod" 操作
     */
    private String initMethodName;

    private String destroyMethodName;

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

    public String getInitMethodName() {
        return initMethodName;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    public String getDestroyMethodName() {
        return destroyMethodName;
    }

    public void setDestroyMethodName(String destroyMethodName) {
        this.destroyMethodName = destroyMethodName;
    }


    /**
     * 新增 scope 属性的 getter 和 setter 方法
     * @return
     */
    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
        this.singleton = this.SCOPE_SINGLETON.equals(scope);
        this.prototype = this.SCOPE_PROTOTYPE.equals(scope);
    }

    public boolean isSingleton() {
        return singleton;
    }

    public boolean isPrototype() {
        return prototype;
    }
}
