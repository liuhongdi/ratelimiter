package com.ratelimiter.demo.util;

import com.ratelimiter.demo.constant.ResponseCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;

public class ServerResponseUtil implements Serializable{

    private static final long serialVersionUID = 7498483649536881777L;

    private Integer status;

    private String msg;

    private Object data;

    public ServerResponseUtil() {
    }

    public ServerResponseUtil(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return this.status == ResponseCode.SUCCESS.getCode();
    }

    public static ServerResponseUtil success() {
        return new ServerResponseUtil(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(), null);
    }

    public static ServerResponseUtil success(String msg) {
        return new ServerResponseUtil(ResponseCode.SUCCESS.getCode(), msg, null);
    }

    public static ServerResponseUtil success(Object data) {
        return new ServerResponseUtil(ResponseCode.SUCCESS.getCode(), null, data);
    }

    public static ServerResponseUtil success(String msg, Object data) {
        return new ServerResponseUtil(ResponseCode.SUCCESS.getCode(), msg, data);
    }

    public static ServerResponseUtil error(String msg) {
        return new ServerResponseUtil(ResponseCode.ERROR.getCode(), msg, null);
    }

    public static ServerResponseUtil error(Object data) {
        return new ServerResponseUtil(ResponseCode.ERROR.getCode(), null, data);
    }

    public static ServerResponseUtil error(String msg, Object data) {
        return new ServerResponseUtil(ResponseCode.ERROR.getCode(), msg, data);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}