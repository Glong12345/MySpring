package com.example.myspring.beans;

import java.util.ArrayList;

/**
 * 属性值集合
 * 考虑到一个类中的属性可能有很多，采用集合类包装一下
 */
public class PropertyValues {

    /**
     * 属性池、存放每个属性值
     */
    private final ArrayList<PropertyValue> propertyValueList = new ArrayList<>();

    public void addPropertyValue(PropertyValue py){
        this.propertyValueList.add(py);
    }

    /**
     * 获取所有属性值
     * @return
     */
    public PropertyValue[] getPropertyValues(){
        return this.propertyValueList.toArray(new PropertyValue[0]);
    }

    /**
     * 根据属性名获取属性值
     */
    public PropertyValue getPropertyValueByName(String name){
        for (PropertyValue value : propertyValueList) {
            if (value.getName().equals(name)){
               return value;
            }
        }
        return null;
    }

}
