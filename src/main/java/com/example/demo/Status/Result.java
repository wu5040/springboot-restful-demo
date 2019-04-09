package com.example.demo.Status;

import com.alibaba.fastjson.JSONObject;

public class Result {
    private String message;
    private int code;
    private JSONObject userInfo;

    public Result(String message, int code) {
        this.message = message;
        this.code = code;
        this.userInfo = null;
    }

    public Result(String message, int code, JSONObject userInfo) {
        this.message = message;
        this.code = code;
        this.userInfo = userInfo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public JSONObject getUserInfo(){
        return userInfo;
    }

    public enum StatusCode {
        /**
         * 成功
         */
        SUCCESS(200),

        /**
         * 失败
         */
        FAIL(400),


        /**
         * 用户没有权限
         */

        Unauthorized(401),

        /**
         * 用户不存在
         */
        USER_NOT_FOUND(40401),

        /**
         * 用户已存在
         */
        USER_ALREADY_EXIST(40001),


        /**
         * 密码错误
         */
        PASSWORD_ERROR(40101),
        ;

        private int code;

        public int getCode() {
            return code;
        }

        StatusCode(int code) {
            this.code = code;
        }
    }
}
