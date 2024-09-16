package com.example.myspring.beans.factory.config;

/**
 * 单例bean注册接口
 */
public interface SingletonBeanRegistry {

    /**
     *
     * @param beanName
     * @return
     */
    Object getSingleton(String beanName);


}
