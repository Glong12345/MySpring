package com.example.myspring.context.support;

import com.example.myspring.beans.BeansException;
import com.example.myspring.beans.factory.config.BeanPostProcessor;
import com.example.myspring.context.ApplicationContext;
import com.example.myspring.context.ApplicationContextAware;

/**
 * 用于处理 ApplicationContextAware 接口的 BeanPostProcessor，感知到 ApplicationContext
 *
 * ApplicationContext 写入到一个包装的 BeanPostProcessor 中去，
 * 再由 AbstractAutowireCapableBeanFactory.applyBeanPostProcessorsBeforeInitialization 方法调用
 */
public class ApplicationContextAwareProcessor implements BeanPostProcessor {

    private final ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ApplicationContextAware){
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
