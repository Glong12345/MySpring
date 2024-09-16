package com.example.myspring;

import com.example.myspring.beans.PropertyValue;
import com.example.myspring.beans.PropertyValues;
import com.example.myspring.beans.factory.config.BeanDefinition;
import com.example.myspring.beans.factory.config.BeanReference;
import com.example.myspring.beans.factory.support.DefaultListableBeanFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MySpringApplicationTests {

    @Test
    void contextLoads() {
    }

    /**
     * 01-测试基本的bean注册-获取bean功能
     */
    @Test
    void test02() {
//        // 先实例化bean工厂
//        BeanFactory beanFactory = new BeanFactory();
//
//        // 注册bean
//        BeanDefinition beanDefinition = new BeanDefinition(new User());
//        beanFactory.registerBeanDefinition("User",beanDefinition);
//
//        // 获取bean
//        User user = (User)beanFactory.getBean("User");
//        user.queryUser();
    }


    /**
     * 测试(不含构造函数)bean的定义、注册、获取
     */
    @Test
    void test03() {
        // 定义bean
        BeanDefinition userDefinition = new BeanDefinition(User.class);
        // 注册bean
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("user", userDefinition);

        // 第一次获取bean（反射创建）
        User user = (User) beanFactory.getBean("user");
        user.queryUser();

        // 第二次获取bean（从单例池缓存中获取）
        User user_singleton = (User) beanFactory.getBean("user");
        user.queryUser();

    }

    /**
     * 测试(含构造函数)通过策略模式选择不同的构造函数进行bean的定义、注册、获取
     */
    @Test
    void test04() {
//        // 定义bean
//        BeanDefinition beanDefinition = new BeanDefinition(User.class);
//
//        // 初始化工厂,注入bean
//        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
//        beanFactory.registerBeanDefinition("user", beanDefinition);
//
//        // 获取bean
//        User bean = (User) beanFactory.getBean("user", "张三");
//        bean.queryUser();


        /**
         * 测试属性注入
         */
        // 定义bean工程
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 注册bean
        beanFactory.registerBeanDefinition("userService", new BeanDefinition(UserServer.class));

        // 注册带有属性的bean
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("name", "张三"));
        propertyValues.addPropertyValue(new PropertyValue("userServer", new BeanReference("userServer")));
        beanFactory.registerBeanDefinition("user", new BeanDefinition(User.class, propertyValues));

        User user = (User) beanFactory.getBean("user");
        user.queryUser();

    }


    @Test
    void test05() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 注册bean
        beanFactory.registerBeanDefinition("userServer", new BeanDefinition(UserServer.class));

        // 注册带有属性的bean
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("name", "张三"));
        propertyValues.addPropertyValue(new PropertyValue("userServer", new BeanReference("userServer")));
        beanFactory.registerBeanDefinition("user", new BeanDefinition(User.class, propertyValues));

        User user = (User) beanFactory.getBean("user");
        user.queryUser();

    }



}
