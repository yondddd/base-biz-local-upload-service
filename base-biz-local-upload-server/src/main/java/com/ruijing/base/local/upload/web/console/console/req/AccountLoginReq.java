package com.ruijing.base.local.upload.web.console.console.req;

import java.io.Serializable;

/**
 * @Description:
 * @Author: WangJieLong
 * @Date: 2024-09-23
 */
public class AccountLoginReq implements Serializable {
    
    private static final long serialVersionUID = -2081554668627336118L;
    
    private String username;
    private String password;
    
    public String getUsername() {
        return username;
    }
    
    public AccountLoginReq setUsername(String username) {
        this.username = username;
        return this;
    }
    
    public String getPassword() {
        return password;
    }
    
    public AccountLoginReq setPassword(String password) {
        this.password = password;
        return this;
    }
    
    @Override
    public String toString() {
        return "AccountLoginReq{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
