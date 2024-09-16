package com.example.myspring.core.io;

import cn.hutool.core.lang.Assert;

import java.net.URL;

/**
 * 资源加载器的具体实现
 */
public class DefaultResourceLoader implements ResourceLoader{

    /**
     * 具体的资源加载器，根据不同的协议加载不同的资源
     * @param location
     * @return
     *
     * 在获取资源的实现中，主要是把三种不同类型的资源处理方式进行了包装，分为：判断是否为ClassPath、URL以及文件。
     * 虽然 DefaultResourceLoader 类实现的过程简单，但这也是设计模式约定的具体结果，像是这里不会让外部调用放知道过多的细节，而是仅关心具体调用结果即可。
     */
    @Override
    public Resource getResource(String location) {
        Assert.notNull(location, "Location must not be null"); // 断言

        if (location.startsWith(CLASSPATH_URL_PREFIX)){
            return new ClassPathResource(location.substring(CLASSPATH_URL_PREFIX.length()));
        }else {
            try {
                URL url = new URL(location);
                return new UrlResource(url);
            }catch (Exception e){
                return new ClassPathResource(location);
            }
        }
    }
}
