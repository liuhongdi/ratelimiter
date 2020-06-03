package com.ratelimiter.demo.controller;

import com.ratelimiter.demo.annotation.RedisRateLimiter;
import com.ratelimiter.demo.util.ServerResponseUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rate")
public class RateController {

    @RequestMapping("/redislimit")
    @RedisRateLimiter(count = 3, time = 1)
    public Object redisLimit() {
        return ServerResponseUtil.success();
    }
}
