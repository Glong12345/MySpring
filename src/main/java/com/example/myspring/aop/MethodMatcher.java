package com.example.myspring.aop;

import java.lang.reflect.Method;

/**
 * Part of a {@link Pointcut}: Checks whether the target method is eligible for advice.
 *
 * 定义方法匹配器接口
 */
public interface MethodMatcher {

    /**
     * Determine whether the given method matches.
     * @param method the candidate method
     * @param targetClass the target class (may be {@code null}, in which case
     * the candidate class must be taken into account)
     * @return whether or not the given method matches
     */
    boolean matches(Method method, Class<?> targetClass);
}
