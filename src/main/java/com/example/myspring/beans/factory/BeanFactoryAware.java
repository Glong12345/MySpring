package com.example.myspring.beans.factory;


import com.example.myspring.beans.BeansException;

/**
 *  * Interface to be implemented by beans that wish to be aware of their
 *  * owning {@link BeanFactory}.
 *
 * 标记接口，实现此接口，既能感知到所属的 BeanFactory
 */
public interface BeanFactoryAware extends Aware{
    void setBeanFactory(BeanFactory beanFactory) throws BeansException;

}
