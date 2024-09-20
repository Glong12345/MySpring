package com.example.myspring.beans.factory;

/**
 * Interface to be implemented by objects used within a {@link BeanFactory}
 * which are themselves factories. If a bean implements this interface,
 * it is used as a factory for an object to expose, not directly as a bean
 * instance that will be exposed itself.
 *
 * @param <T>
 */
public interface FactoryBean<T> {

    /**
     * 获取对象
     * @return
     * @throws Exception
     */
    T getObject() throws Exception;

    /**
     * 获取对象的类型
     * @return
     */
    Class<?> getObjectType();

    /**
     * 是否单例
     * @return
     */
    boolean isSingleton();

}
