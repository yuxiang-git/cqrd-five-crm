package com.cqrd.five.dto;

import java.io.Serializable;

public class User implements Serializable {
    //用户id
    private Integer userId;
    //用户名
    private String userName;
    //密码
    private String passWord;
    //中文名
    private String chineseName;
    //邮箱
    private String email;
    //电话
    private String phone;
    //git账号
    private String gitAccount;
    //成绩
    private Double score;
    //学分
    private Double credit;
    //验证码
    private String code;
    //权限码(1为管理员，2为普通用户)
    private Integer access;
    //状态码（1为登录，0为未登录）
    private Integer status;

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", chineseName='" + chineseName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", gitAccount='" + gitAccount + '\'' +
                ", score=" + score +
                ", credit=" + credit +
                ", code='" + code + '\'' +
                ", access=" + access +
                ", status=" + status +
                '}';
    }

    public User() {
    }

    public User(Integer userId, String userName, String passWord, String chineseName, String email, String phone, String gitAccount, Double score, Double credit, String code, Integer access, Integer status) {
        this.userId = userId;
        this.userName = userName;
        this.passWord = passWord;
        this.chineseName = chineseName;
        this.email = email;
        this.phone = phone;
        this.gitAccount = gitAccount;
        this.score = score;
        this.credit = credit;
        this.code = code;
        this.access = access;
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGitAccount() {
        return gitAccount;
    }

    public void setGitAccount(String gitAccount) {
        this.gitAccount = gitAccount;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getAccess() {
        return access;
    }

    public void setAccess(Integer access) {
        this.access = access;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
