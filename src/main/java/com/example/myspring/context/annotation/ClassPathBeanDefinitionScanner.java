package com.example.myspring.context.annotation;

import cn.hutool.core.util.StrUtil;
import com.example.myspring.beans.factory.config.BeanDefinition;
import com.example.myspring.beans.factory.support.BeanDefinitionRegistry;
import com.example.myspring.stereotype.Component;

import java.util.Set;

/**
 * A bean definition scanner that detects bean candidates on the classpath,
 * registering corresponding bean definitions with a given registry ({@code BeanFactory}
 * or {@code ApplicationContext}).
 */
public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {

    private BeanDefinitionRegistry registry;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void doScan(String... basePackages) {
        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
            for (BeanDefinition beanDefinition : candidates) {
                // 解析 Bean 的作用域 scope： singleton、prototype
                String beanScope = resolveBeanScope(beanDefinition);
                if (StrUtil.isNotEmpty(beanScope)) {
                    beanDefinition.setScope(beanScope);
                }
                // 注册 Bean
                registry.registerBeanDefinition(determineBeanName(beanDefinition), beanDefinition);
            }
        }

    }

    /**
     * 解析 Bean 的作用域 scope： singleton、prototype
     *
     * @param beanDefinition
     * @return
     */
    private String resolveBeanScope(BeanDefinition beanDefinition) {
        // 解析 Bean 的作用域 scope： singleton、prototype
        Class<?> beanClass = beanDefinition.getBeanClass();
        Scope scope = beanClass.getAnnotation(Scope.class);

        if (null != scope) return scope.value();

        return StrUtil.EMPTY;
    }

    /**
     * 解析配置的 Bean 的名称
     *
     * @param beanDefinition
     * @return
     */
    private String determineBeanName(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Component component = beanClass.getAnnotation(Component.class);
        String value = component.value(); // 如果没有指定，默认使用类名首字母小写
        if (StrUtil.isEmpty(value)) {
            value = StrUtil.lowerFirst(beanClass.getSimpleName());
        }

        return value;
    }


}
