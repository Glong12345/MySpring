package com.example.myspring.beans.factory.xml;

import com.example.myspring.beans.BeansException;
import com.example.myspring.beans.factory.ListableBeanFactory;
import com.example.myspring.beans.factory.config.AutowireCapableBeanFactory;
import com.example.myspring.beans.factory.config.BeanDefinition;
import com.example.myspring.beans.factory.config.ConfigurableBeanFactory;

/**
 * Configuration interface to be implemented by most listable bean factories.
 * In addition to {@link ConfigurableBeanFactory}, it provides facilities to
 * analyze and modify bean definitions, and to pre-instantiate singletons.
 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {

    BeanDefinition getBeanDefinition(String beanName) throws BeansException;
}
