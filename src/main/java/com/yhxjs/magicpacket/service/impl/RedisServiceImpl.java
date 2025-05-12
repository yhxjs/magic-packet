package com.yhxjs.magicpacket.service.impl;

import com.yhxjs.magicpacket.service.RedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void set(String key, Object value, long expire, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, expire, timeUnit);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
