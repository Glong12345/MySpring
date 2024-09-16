package com.example.myspring.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * core.io 处理资源加载流
 * 该接口提供获取输入InputStream流的方法
 */
public interface Resource {

    /**
     * 获取输入流
     * @return
     * @throws IOException
     */
    InputStream getInputStream() throws IOException;
}
