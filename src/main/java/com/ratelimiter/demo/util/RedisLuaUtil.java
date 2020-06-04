package com.ratelimiter.demo.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.annotation.Resource;
import java.util.*;

@Service
public class RedisLuaUtil {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    //private static final Logger logger = LoggerFactory.getLogger("ratelimiterLogger");
    private static final Logger logger = LogManager.getLogger("bussniesslog");
    /*
    run a lua script
    luaFileName: lua file name,no path
    keyList: list for redis key
    return 0: fail
           1: success
    */
    public String runLuaScript(String luaFileName,List<String> keyList) {
        DefaultRedisScript<String> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/"+luaFileName)));
        redisScript.setResultType(String.class);
        String result = "";
        String argsone = "none";
        //logger.error("开始执行lua");
        try {
            result = stringRedisTemplate.execute(redisScript, keyList,argsone);
        } catch (Exception e) {
            logger.error("发生异常",e);
        }

        return result;
    }
}
