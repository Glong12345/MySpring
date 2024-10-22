package com.example.myspring.util;

/**
 * Simple strategy interface for resolving a String value.
 * Used by {com.example.myspring.beans.factory.config.ConfigurableBeanFactory}.
 *
 */
public interface StringValueResolver {

    // 根据传入的字符串解析出对应的值
    String resolveStringValue(String strVal);
}
