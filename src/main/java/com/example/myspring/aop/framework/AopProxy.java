package com.example.myspring.aop.framework;

/**
 * Delegate interface for a configured AOP proxy, allowing for the creation
 * of actual proxy objects.
 *
 * <p>Out-of-the-box implementations are available for JDK dynamic proxies
 * and for CGLIB proxies, as applied by DefaultAopProxyFactory
 *
 *
 * <p>AOP 代理接口的抽象定义  </p>
 * 定义一个标准接口，用于获取代理类,具体实现代理的方式可以有 JDK 方式，也可以是 Cglib 方式。
 */
public interface AopProxy {

    // 获取代理对象
    Object getProxy();
}
