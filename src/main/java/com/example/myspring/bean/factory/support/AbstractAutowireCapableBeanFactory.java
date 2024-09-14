package com.example.myspring.bean.factory.support;

import cn.hutool.core.bean.BeanUtil;
import com.example.myspring.bean.BeansException;
import com.example.myspring.bean.PropertyValue;
import com.example.myspring.bean.PropertyValues;
import com.example.myspring.bean.factory.config.BeanDefinition;
import com.example.myspring.bean.factory.config.BeanReference;
import com.example.myspring.bean.factory.support.instantiation.CglibSubclassingInstantiationStrategy;
import com.example.myspring.bean.factory.support.instantiation.SimpleInstantiationStrategy;

import java.lang.reflect.Constructor;

/**
 * 该类是支持自动装配的Bean工厂，主要用于通过bean的类型创建Bean实例
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    // 实例化策略属性类,这里是通过Cglib实现类来实例化对象
    private SimpleInstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();

    /**
     * 创建Bean实例
     *
     * @param beanName
     * @param beanDefinition
     * @return
     */
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) {
        Object bean = null;

        //  通过反射实例化对象
        try {
//            bean = beanDefinition.getBeanClass().newInstance();
//            考虑到构造器参数，需要通过构造器来实例化对象
            bean = createBeanInstance(beanDefinition, beanName, args);

            // 设置bean的属性值
            applyPropertyValues(beanName, bean, beanDefinition);

        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        // 将bean实例化后放入单例池缓存中
        addSingleton(beanName, bean);
        return bean;
    }

    /**
     * 创建bean（通过反射创建纯净bean）
     *
     * @param beanDefinition
     * @param beanName
     * @param args
     * @return
     */
    protected Object createBeanInstance(BeanDefinition beanDefinition, String beanName, Object[] args) {
        Constructor constructorToUse = null;
        // 获取bean的构造器
        Class beanClass = beanDefinition.getBeanClass();
        Constructor[] constructors = beanClass.getConstructors();
        for (Constructor ctor : constructors) {
            if (null != args && ctor.getParameterTypes().length == args.length) {
                constructorToUse = ctor;
                break;
            }
        }

        return getInstantiationStrategy().instantiate(beanDefinition, beanName, constructorToUse, args);
    }

    /**
     * 设置bean的属性值
     *
     * @param beanName
     * @param bean
     * @param beanDefinition
     */
    protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        try {
            PropertyValues propertyValues = beanDefinition.getPropertyValues();
            if (null != propertyValues) {
                // 如果beanDefinition中存在属性值，则循环进行属性赋值
                for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                    String name = propertyValue.getName();
                    Object value = propertyValue.getValue();

                    if (value instanceof BeanReference) {
                        // 如果是bean引用，则从单例池中获取bean实例
                        BeanReference beanReference = ((BeanReference) value);
                        value = getBean(beanReference.getBeanName());
                    }

                    // 设置bean的属性值
                    BeanUtil.setFieldValue(bean, name, value);
                }
            }
        } catch (Exception e) {
            throw new BeansException("Error setting property values on bean: " + beanName);
        }
    }


    /**
     * 获取实例化策略
     *
     * @return
     */
    public SimpleInstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(SimpleInstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }
}
