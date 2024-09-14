package com.example.myspring;

public class User {
    // 测试基本类型
    private String name;

    // 测试引用类型
    private UserServer userServer;

    public void queryUser() {
        System.out.println("正在查询用户信息");
    }

    public User() {

    }

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserServer getUserServe() {
        return userServer;
    }

    public void setUserServe(UserServer userServe) {
        this.userServer = userServe;
    }
}
