package com.example.myspring.beans.factory.support;

import com.example.myspring.beans.BeansException;
import com.example.myspring.core.io.Resource;
import com.example.myspring.core.io.ResourceLoader;

/**
 * bean的定义读取器接口
 */
public interface BeanDefinitionReader {

    /**
     * getRegistry()、getResourceLoader()，都是用于提供给后面三个方法的工具，加载和注册
     * 这两个方法的实现会包装到抽象类中，以免污染具体的接口实现方法。
     * @return
     */
    BeanDefinitionRegistry getRegistry();

    ResourceLoader getResourceLoader();

    /**
     * 定义了三个加载bean定义的方法，通过不同的Resource来加载
     * @param resource
     * @throws BeansException
     */
    void loadBeanDefinitions(Resource resource) throws BeansException;

    void loadBeanDefinitions(Resource... resources) throws BeansException;

    void loadBeanDefinitions(String location) throws BeansException;

}
