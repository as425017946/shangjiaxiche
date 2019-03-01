package com.mdb.example.administrator.bean;

/**
 * 通用版本使用
 */
public class TongyongBean {

    /**
     * message : 请求成功
     * state : 1
     */

    private String message;
    private int state;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
