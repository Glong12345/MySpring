package com.example.myspring;

import java.util.HashMap;
import java.util.Map;

public class UserServer {
    private static Map<String, String> userMap = new HashMap<>();

    static {
        userMap.put("1001","gl");
        userMap.put("1002","kx");
        userMap.put("1003","kl");
    }
    public String qureyUserName(String uId){
        return userMap.get(uId);
    }
}
