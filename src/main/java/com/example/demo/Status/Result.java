package com.example.demo.Status;

import com.alibaba.fastjson.JSONObject;

public class Result {
    private String message;
    private int code;
    private JSONObject jsonObject;
    private Object object;

    public Result(String message, int code) {
        this.message = message;
        this.code = code;
        this.jsonObject = null;
        this.object=null;
    }

    public Result(String message, int code, JSONObject jsonObject,Object object) {
        this.message = message;
        this.code = code;
        this.jsonObject = jsonObject;
        this.object=object;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
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

    public JSONObject getJsonObject(){
        return jsonObject;
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
         * 开课表中课程不存在
         */
        OPEN_NOT_FOUND(40402),


        /**
         * 用户已存在
         */
        USER_ALREADY_EXIST(40001),

        /**
         * 所选课程已存在
         */
        ELECTIVE_ALREADY_EXIST(40002),


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
