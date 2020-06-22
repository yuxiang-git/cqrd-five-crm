package com.cqrd.five.ex;

/**
 * 自定制异常类
 * @author shkstart
 * @create 2019-11-17 14:52
 */
public class CustomException extends RuntimeException {
    private int code;
    private String message;
    public CustomException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public CustomException(ResultStatusEnum resultStatusEnum) {
        this.code = resultStatusEnum.getCode();
        this.message = resultStatusEnum.getMessage();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
