package com.example.myspring.beans.factory.support;

import com.example.myspring.beans.BeansException;
import com.example.myspring.beans.factory.config.BeanDefinition;
import com.example.myspring.beans.factory.ConfigurableListableBeanFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 该类实现注册Bean定义与获取Bean定义就可以同时使用了
 * 接口定义了注册，抽象类定义了获取
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {

    //beanDefinitionMap用来保存BeanDefinition
    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    // 根据beanName获取BeanDefinition
    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null) {
            throw new BeansException("No bean named '" + beanName + "' is defined");
        }
        return beanDefinition;
    }

    // 将BeanDefinition注册到beanDefinitionMap中
    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    // 根据beanName判断是否存在BeanDefinition
    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        Map<String, T> result = new HashMap<>();
        beanDefinitionMap.forEach((beanName, beanDefinition) -> {
            Class beanClass = beanDefinition.getBeanClass();
            if (type.isAssignableFrom(beanClass)) {
                result.put(beanName, (T) getBean(beanName));
            }
        });
        return result;
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return beanDefinitionMap.keySet().toArray(new String[0]);
    }

    @Override
    public void preInstantiateSingletons() throws BeansException {
        beanDefinitionMap.keySet().forEach(this::getBean);
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        ArrayList<String> beanNames = new ArrayList<>();
        for (Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            Class beanClass = entry.getValue().getBeanClass();
            if (requiredType.isAssignableFrom(beanClass)) {
                beanNames.add(entry.getKey());
            }
        }

        if (1 == beanNames.size()){
            return getBean(beanNames.get(0),requiredType);
        }

        throw new BeansException(requiredType + "expected single bean but found " + beanNames.size() + ": " + beanNames);
    }
}
