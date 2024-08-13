package com.example.myspring;

public class User {
    private String name;
    public void queryUser(){
        System.out.println("正在查询用户信息");
    }

    public  User(){

    }

    public User(String name) {
        this.name = name;
    }

}
