package com.example.myspring.bean.factory.support;

import com.example.myspring.bean.BeansException;
import com.example.myspring.bean.factory.config.BeanDefinition;
import com.example.myspring.bean.factory.support.instantiation.CglibSubclassingInstantiationStrategy;
import com.example.myspring.bean.factory.support.instantiation.SimpleInstantiationStrategy;

import java.lang.reflect.Constructor;

/**
 * 该类是支持自动装配的Bean工厂，主要用于通过bean的类型创建Bean实例
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    // 实例化策略属性类,这里是通过Cglib实现类来实例化对象
    private CglibSubclassingInstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

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
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        // 将bean实例化后放入单例池缓存中
        addSingleton(beanName, bean);
        return bean;
    }

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

    public CglibSubclassingInstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(CglibSubclassingInstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }
}
