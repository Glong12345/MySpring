package com.example.myspring.beans.factory.config;

/**
 * 单例bean注册接口
 */
public interface SingletonBeanRegistry {

    /**
     *  获取单例bean
     * @param beanName
     * @return
     */
    Object getSingleton(String beanName);


    /**
     * 销毁单例bean
     */
    void destroySingletons();


    /**
     * 注册单例bean
     * @param beanName
     * @param singletonObject
     */
    void registerSingleton(String beanName, Object singletonObject);

}
