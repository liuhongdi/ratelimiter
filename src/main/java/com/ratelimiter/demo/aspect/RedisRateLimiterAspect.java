package com.ratelimiter.demo.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ratelimiter.demo.annotation.RedisRateLimiter;
import com.ratelimiter.demo.constant.ResponseCode;
import com.ratelimiter.demo.util.RedisLuaUtil;
import com.ratelimiter.demo.util.ServerResponseUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Component
@Aspect
public class RedisRateLimiterAspect {
    //for json
    private static ObjectMapper objectMapper = new ObjectMapper();

    @Resource
    private RedisLuaUtil redisLuaUtil;

    @Pointcut("@annotation(com.ratelimiter.demo.annotation.RedisRateLimiter)")
    private void pointcut() {}

    /*
    *   around,
    *   if reach limit in time
    *   return error info
    * */
    @Around(value = "pointcut()")
    public Object requestLimit(ProceedingJoinPoint joinPoint) throws Exception {

        Object[] args = joinPoint.getArgs();
        try {

            Signature signature = joinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature)signature;
            //获取目标方法
            Method targetMethod = methodSignature.getMethod();
            String method_name = targetMethod.getName();
            //System.out.println("method_name:"+method_name);
            if (targetMethod.isAnnotationPresent(RedisRateLimiter.class)) {
                //获取目标方法的@LxRateLimit注解
                RedisRateLimiter limit = targetMethod.getAnnotation(RedisRateLimiter.class);

                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
                String ip = request.getRemoteAddr();
                String key = "req_limit_".concat(method_name).concat("_").concat(ip);
                boolean checkResult = checkByRedis(limit, key);

                if (checkResult) {
                    return joinPoint.proceed();
                } else {
                    return objectMapper.writeValueAsString(ServerResponseUtil.error(ResponseCode.ACCESS_LIMIT.getMsg()));
                }
            } else {
                return joinPoint.proceed();
            }

            //return ret;
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }

    }


    /*
    * check is reach limit in time
    * run by lua
    * */
    private boolean checkByRedis(RedisRateLimiter limit, String key) {

        List<String> keyList = new ArrayList();
        keyList.add(key);
        keyList.add(String.valueOf(limit.count()));
        keyList.add(String.valueOf(limit.time()));
        String res = redisLuaUtil.runLuaScript("ratelimit.lua",keyList);
        System.out.println("------------------lua res:"+res);
        if (res.equals("1")) {
            return true;
        } else {
            return false;
        }

    }
}