package com.lwz.demo.controller.user.util;

import javax.annotation.Resource;

import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;

/**
 *
 * @author xcbeyond
 * 2018年7月19日下午3:08:04
 */

//@SpringBootTest
public class RedisTest {

    @Resource
    RedisTemplate<String, String> redisTemplate;
    @Resource
    RedisUtil redisUtil;
    /**
     * 插入缓存数据
     */

    @Test
    public void testRedis(){
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("http://localhost:6379");
        System.out.println("连接成功");
        //查看服务是否运行
        System.out.println("服务正在运行: "+jedis.ping());
        System.out.println("======================key==========================");
        //清除当前数据库所有数据

        //设置键值对
        jedis.set("xiaozhe","我是小哲");
        //查看存储的键的总数
        System.out.println(jedis.dbSize());
        //取出设置的键值对并打印
        System.out.println(jedis.get("xiaozhe"));
    }
}