package com.example.myspring;

public class User {


    private String uId;

    // 测试引用类型
    private UserServer userServer;

    public String queryUser() {
        return userServer.qureyUserName(uId);
    }

    public User() {

    }

    public User(String uId) {
        this.uId = uId;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public UserServer getUserServe() {
        return userServer;
    }

    public void setUserServe(UserServer userServe) {
        this.userServer = userServe;
    }
}
