package com.example.myspring.common;

import com.example.myspring.bean.UserService;
import com.example.myspring.beans.BeansException;
import com.example.myspring.beans.PropertyValue;
import com.example.myspring.beans.PropertyValues;
import com.example.myspring.beans.factory.ConfigurableListableBeanFactory;
import com.example.myspring.beans.factory.config.BeanFactoryPostProcessor;
import com.example.myspring.beans.factory.config.BeanDefinition;
import com.example.myspring.beans.factory.config.BeanPostProcessor;

public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if ("userService".equals(beanName)) {
            UserService userService = (UserService) bean;
            userService.setLocation("改为：北京");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

}
