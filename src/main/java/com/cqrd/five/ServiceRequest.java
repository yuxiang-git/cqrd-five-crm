package com.cqrd.five;

import java.io.Serializable;

public class ServiceRequest implements Serializable {
    private Integer userId;
    private String userName;
    private String passWord;
    private String chineseName;
    private String email;
    private String phone;
    private String gitAccount;
    private double score;
    private double credit;
    private String code;
    private Integer access;
    private Integer status;

    private ServiceRequest(String userName){
        this.userName = userName;
    }
    private ServiceRequest(String userName,String passWord){
        this.userName = userName;
        this.passWord = passWord;
    }
    private ServiceRequest(String userName,String passWord,String chineseName,String email,String phone,String gitAccount){
        this.userName = userName;
        this.passWord = passWord;
        this.chineseName = chineseName;
        this.email = email;
        this.phone = phone;
        this.gitAccount = gitAccount;
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

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
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

    public ServiceRequest() {
    }

    public ServiceRequest(Integer userId, String userName, String passWord, String chineseName, String email, String phone, String gitAccount, double score, double credit, String code, Integer access, Integer status) {
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
}
