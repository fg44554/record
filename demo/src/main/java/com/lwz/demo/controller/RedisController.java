package com.lwz.demo.controller;

import com.lwz.demo.controller.user.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
@Controller
public class RedisController {
    @Autowired
    RedisTemplate<String, String> redisTemplate;
    @Autowired
    RedisUtil redisUtil;
    @RequestMapping("/redis")
    public void redis(){
        redisUtil.set("LiYi","user");
        System.out.println(redisUtil.get("LiYi"));
    }
}
