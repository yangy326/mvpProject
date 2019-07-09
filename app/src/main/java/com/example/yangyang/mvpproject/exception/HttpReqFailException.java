package com.example.yangyang.mvpproject.exception;

public class HttpReqFailException extends RuntimeException {

    private int errorCode = 200;
    private String msg = "";

    public HttpReqFailException(int errorCode, String msg) {
        this.errorCode = errorCode;
        this.msg = msg;
    }

    public HttpReqFailException(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "请求失败，errorCode为 " + errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMsg() {
        return msg;
    }
}
