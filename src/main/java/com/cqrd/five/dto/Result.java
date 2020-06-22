package com.cqrd.five.dto;
/**
 * 向前端返回信息封装
 * @param <T> 可变类型
 */
public class Result<T>{
        //返回信息
        private String msg;
        //数据是否正常请求
        private boolean success;
        //具体返回的数据
        private T detail;
        //返回非法输入
        private boolean illegal;

        private String token;
        private UserInfo userInfo;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public boolean isIllegal() {
        return illegal;
    }

    public void setIllegal(boolean illegal) {
        this.illegal = illegal;
    }



    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getDetail() {
        return detail;
    }

    public void setDetail(T detail) {
        this.detail = detail;
    }
}
