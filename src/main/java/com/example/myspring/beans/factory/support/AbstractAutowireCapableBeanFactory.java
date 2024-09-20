package com.example.myspring.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.example.myspring.beans.BeansException;
import com.example.myspring.beans.PropertyValue;
import com.example.myspring.beans.PropertyValues;
import com.example.myspring.beans.factory.*;
import com.example.myspring.beans.factory.config.AutowireCapableBeanFactory;
import com.example.myspring.beans.factory.config.BeanDefinition;
import com.example.myspring.beans.factory.config.BeanPostProcessor;
import com.example.myspring.beans.factory.config.BeanReference;
import com.example.myspring.beans.factory.support.instantiation.SimpleInstantiationStrategy;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * 该类是支持自动装配的Bean工厂，主要用于通过bean的类型创建Bean实例
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

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

            // 执行bean的初始化方法和BeanPostProcessor的前置和后置处理方法
            bean = initializeBean(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        // 新增--注册实现了 DisposableBean 接口的 bean 的对象
        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);

        // 将bean实例化后放入单例池缓存中
        // 放入单例池前判断 bean 作用域是否事单例 如果是原型模式那么就不会存放到内存中，每次获取都重新创建对象，另外非 Singleton 类型的 Bean 不需要执行销毁方法
        if (beanDefinition.isSingleton()) {
            addSingleton(beanName, bean);
        }
        return bean;
    }

    private void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        // 新增-非单例 bean 不销毁
        if (!beanDefinition.isSingleton()){
            return;
        }

        if (bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())) {
            registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
        }
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

    private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {

        // invokeAwareMethods
        if (bean instanceof Aware) {
            if (bean instanceof BeanFactoryAware) {
                ((BeanFactoryAware) bean).setBeanFactory(this);
            }
            if (bean instanceof BeanClassLoaderAware){
                ((BeanClassLoaderAware) bean).setBeanClassLoader(getBeanClassLoader());
            }
            if (bean instanceof BeanNameAware) {
                ((BeanNameAware) bean).setBeanName(beanName);
            }
        }


        // 1. 执行 BeanPostProcessor Before 处理
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);

        // 执行 Bean 对象的初始化方法
        try {
            invokeInitMethods(beanName, wrappedBean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Invocation of init method of bean[" + beanName + "] failed", e);
        }

        // 2. 执行 BeanPostProcessor After 处理
        wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
        return wrappedBean;
    }

    /**
     * 两种方法执行初始化，第一种执行 InitializingBean 接口；另外一个是判断配置信息 init-method 是否存在，执行反射调用 initMethod.invoke(bean)
     * @param beanName
     * @param bean
     * @param beanDefinition
     * @throws Exception
     */
    private void invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception {
        // 1. 判断是否是 InitializingBean ，如果是则调用 afterPropertiesSet()
        if (bean instanceof InitializingBean){
            ((InitializingBean) bean).afterPropertiesSet();
        }

        // 2. 判断 bean 是否配置了 init-method（自定义初始化方法）{判断是为了避免二次执行初始化操作}
        String initMethodName = beanDefinition.getInitMethodName();
        if (StrUtil.isNotEmpty(initMethodName) && !(bean instanceof InitializingBean)){
            Method initMethod = beanDefinition.getBeanClass().getMethod(initMethodName);
            initMethod.invoke(bean);
        }

    }

    /**
     * 前置处理，在初始化之前执行
     * @param existingBean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessBeforeInitialization(result, beanName);
            if (null == current) return result;
            result = current;
        }
        return result;
    }

    /**
     * 后置处理，在初始化之后执行
     * @param existingBean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessAfterInitialization(result, beanName);
            if (null == current) return result;
            result = current;
        }
        return result;
    }


}
