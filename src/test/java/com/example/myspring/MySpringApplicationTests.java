package com.example.myspring;

import com.example.myspring.bean.factory.config.BeanDefinition;
import com.example.myspring.bean.factory.support.DefaultListableBeanFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Comparator;
import java.util.PriorityQueue;

@SpringBootTest
class MySpringApplicationTests {

    @Test
    void contextLoads() {
    }

    /**
     * 01-测试基本的bean注册-获取bean功能
     */
    @Test
    void test02(){
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
    void test03(){
        // 定义bean
        BeanDefinition userDefinition = new BeanDefinition(User.class);
        // 注册bean
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("user", userDefinition);

        // 第一次获取bean（反射创建）
        User user = (User)beanFactory.getBean("user");
        user.queryUser();

        // 第二次获取bean（从单例池缓存中获取）
        User user_singleton = (User)beanFactory.getBean("user");
        user.queryUser();

        PriorityQueue<Object> queue = new PriorityQueue(new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                return 0;
            }
        });
    }


}
