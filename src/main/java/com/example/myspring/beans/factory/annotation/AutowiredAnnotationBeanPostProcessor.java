package com.example.myspring.beans.factory.annotation;

import cn.hutool.core.bean.BeanUtil;
import com.example.myspring.beans.BeansException;
import com.example.myspring.beans.PropertyValues;
import com.example.myspring.beans.factory.BeanFactory;
import com.example.myspring.beans.factory.BeanFactoryAware;
import com.example.myspring.beans.factory.ConfigurableListableBeanFactory;
import com.example.myspring.beans.factory.config.BeanPostProcessor;
import com.example.myspring.beans.factory.config.ConfigurableBeanFactory;
import com.example.myspring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.example.myspring.util.ClassUtils;

import java.lang.reflect.Field;

/**
 * {@link BeanPostProcessor} implementation
 * that autowires annotated fields, setter methods and arbitrary config methods.
 * Such members to be injected are detected through a Java 5 annotation: by default,
 * Spring's {@link Autowired @Autowired} and {@link Value @Value} annotations.
 * <p>
 * 处理 @Value、@Autowired，注解的 BeanPostProcessor
 * <p>
 */
public class AutowiredAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    /**
     * 注解的处理
     * 主要用于处理类含有 @Value、@Autowired 注解的属性，进行属性信息的提取和设置。
     *
     * @param pvs
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        // 1. 处理 @Value 注解
        Class<?> clazz = bean.getClass();
        // 解决 CGLIB 代理对象问题
        clazz = ClassUtils.isCglibProxyClass(clazz) ? clazz.getSuperclass() : clazz;

        // 通过反射获取类中所有字段
        Field[] declaredFields = clazz.getDeclaredFields();

        for (Field field : declaredFields) {
            Value valueAnnotation = field.getAnnotation(Value.class);
            if (null != valueAnnotation) {
                // 获取注解中的值
                String value = valueAnnotation.value();
                // 解析替换占位符
                value = beanFactory.resolveEmbeddedValue(value);
                // 设置字段值
                BeanUtil.setFieldValue(bean, field.getName(), value);
            }
        }

        // 2. 处理 @Autowired 注解
        for (Field field : declaredFields) {
            Autowired autowiredAnnotation = field.getAnnotation(Autowired.class);
            if (null != autowiredAnnotation) {
                Class<?> fieldType = field.getType();
                String dependentBeanName = null;
                Qualifier qualifierAnnotation = field.getAnnotation(Qualifier.class);
                Object dependentBean = null;
                if (null != qualifierAnnotation) {
                    dependentBeanName = qualifierAnnotation.value();
                    dependentBean = beanFactory.getBean(dependentBeanName, fieldType);
                }else{
                    dependentBean = beanFactory.getBean(fieldType);
                }
                BeanUtil.setFieldValue(bean, field.getName(), dependentBean);
            }
        }

        return pvs;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }
}
