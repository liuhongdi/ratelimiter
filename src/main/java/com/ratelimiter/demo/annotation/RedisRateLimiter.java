package com.ratelimiter.demo.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisRateLimiter {
    /**
     * 允许访问的次数，默认值20
     */
    int count() default 20;

    /**
     * 时间段，单位为秒，默认值一分钟
     */
    int time() default 60;
}