package com.example.myspring.beans.factory.support;

import com.example.myspring.beans.factory.FactoryBean;
import com.example.myspring.beans.BeansException;
import com.example.myspring.beans.factory.config.BeanDefinition;
import com.example.myspring.beans.factory.config.BeanPostProcessor;
import com.example.myspring.beans.factory.config.ConfigurableBeanFactory;
import com.example.myspring.util.ClassUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽象类定义模板方法,定义通用的bean工厂，要实现bean工厂接口，同时要继承bean的单例注册类，便于注册和获取bean对象
 */
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {

    /**
     * BeanPostProcessors to apply in createBean
     */
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();

    /**
     * ClassLoader to resolve bean class names with, if necessary
     */
    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

    // getBean方法发生了重载，抽取出公共部分，通过doGetBean方法实现
    @Override
    public Object getBean(String beanName) throws BeansException {
        // 这里相当于无参的getBean方法
        return doGetBean(beanName, null);
    }

    @Override
    public Object getBean(String beanName, Object... args) throws BeansException {
        // 这里相当于有参的getBean方法
        return doGetBean(beanName, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return (T) getBean(name);
    }

    /**
     * getBean方法的落地实现，先从单例池中获取，如果单例池中没有，则创建bean对象
     *
     * @param name
     * @param args
     * @param <T>
     * @return
     */
    protected <T> T doGetBean(final String name, final Object[] args) {
        // 先从单例池中获取bean对象
//        Object bean = getSingleton(name);
        Object sharedInstance = getSingleton(name);
        if (sharedInstance != null) {
            // 如果是 FactoryBean，则需要调用 FactoryBean#getObject
            return (T) getObjectForBeanInstance(sharedInstance, name);
        }

        // 如果单例池中没有，则创建bean对象
        // 需要拿到bean的注册信息，然后通过反射创建bean对象
        // 这里需要抽象方法，由子类实现
        BeanDefinition beanDefinition = getBeanDefinition(name);
        Object bean = createBean(name, beanDefinition, args);
        return (T) getObjectForBeanInstance(bean, name);
    }

    /**
     * 获取bean实例，如果bean是FactoryBean，则调用FactoryBean#getObject方法获取实例
     * @param beanInstance
     * @param name
     * @return
     */
    private Object getObjectForBeanInstance(Object beanInstance, String name) {
        // 如果bean不是 FactoryBean，则直接返回
        if (!(beanInstance instanceof FactoryBean)) {
            return beanInstance;
        }

        // 如果bean是 FactoryBean，则需要调用 FactoryBean#getObject
        Object objectForFactoryBean = getCachedObjectForFactoryBean(name);
        if (objectForFactoryBean == null) {
            FactoryBean<?> factoryBean = (FactoryBean<?>) beanInstance;
            objectForFactoryBean = getObjectFromFactoryBean(factoryBean, name);
        }

        return objectForFactoryBean;
    }

    /**
     * 获取bean的注册信息
     *
     * @param beanName
     * @return
     */
    protected abstract BeanDefinition getBeanDefinition(String beanName);

    /**
     * 创建bean对象
     *
     * @param beanName
     * @param beanDefinition
     * @return
     */
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args);


    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    /**
     * Return the list of BeanPostProcessors that will get applied
     * to beans created with this factory.
     */
    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    /**
     * 获取bean的类加载器
     *
     * @return
     */
    public ClassLoader getBeanClassLoader() {
        return beanClassLoader;
    }
}
