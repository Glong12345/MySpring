package com.example.myspring.beans.factory.support;

import com.example.myspring.beans.BeansException;
import com.example.myspring.beans.factory.DisposableBean;
import com.example.myspring.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 单例bean注册表,定义了一个获取单例对象的接口
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    /**
     * Internal marker for a null singleton object:
     * used as marker value for concurrent Maps (which don't support null values).
     */
    protected static final Object NULL_OBJECT = new Object();

    /**
     * 单例bean的缓存
     */
    private Map<String,Object> singletonObjects = new HashMap<>();


    /**
     * 新增--单例bean的销毁方法
     */
    private final Map<String, DisposableBean> disposableBeans = new HashMap<>();

    /**
     * 获取单例bean
     * @param beanName
     * @return
     */
    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    /**
     * 添加单例bean
     * 这个方法可以被继承此类的其他类调用。包括：AbstractBeanFactory 以及继承的 DefaultListableBeanFactory 调用。
     * @param beanName
     * @param singletonObject
     */
    protected void addSingleton(String beanName,Object singletonObject){
        singletonObjects.put(beanName,singletonObject);
    }

    public void registerDisposableBean(String beanName, DisposableBean bean) {
        disposableBeans.put(beanName, bean);
    }


    public void destroySingletons() {
        Set<String> keySet = this.disposableBeans.keySet();
        Object[] disposableBeanNames = keySet.toArray();

        for (int i = disposableBeanNames.length - 1; i >= 0; i--) {
            Object beanName = disposableBeanNames[i];
            DisposableBean disposableBean = disposableBeans.remove(beanName);
            try {
                disposableBean.destroy();
            } catch (Exception e) {
                throw new BeansException("Destroy method on bean with name '" + beanName + "' threw an exception", e);
            }
        }
    }

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName,singletonObject);
    }
}
