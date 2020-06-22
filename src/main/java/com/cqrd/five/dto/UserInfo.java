package com.cqrd.five.dto;

/**
 * 载荷对象
 */
public class UserInfo {

    private String username;

    private int access;

    public UserInfo() {
    }

    public UserInfo(String username, int access) {
        this.username = username;
        this.access = access;
    }

    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}