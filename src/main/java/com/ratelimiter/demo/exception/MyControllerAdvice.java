package com.ratelimiter.demo.exception;

import com.ratelimiter.demo.constant.ResponseCode;
import com.ratelimiter.demo.util.ServerResponseUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class MyControllerAdvice {


    @ResponseBody
    @ExceptionHandler(ServiceException.class)
    public ServerResponseUtil serviceExceptionHandler(ServiceException se) {
        return ServerResponseUtil.error(se.getMsg());
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ServerResponseUtil exceptionHandler(Exception e) {
        return ServerResponseUtil.error(ResponseCode.SERVER_ERROR.getMsg());
    }

}