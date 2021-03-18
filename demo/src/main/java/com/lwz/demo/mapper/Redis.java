package com.lwz.demo.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public class Redis {
    @Autowired
    private RedisTemplate redisTemplate;

}
