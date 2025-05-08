package com.yhxjs.magicpacket.service.impl;

import com.yhxjs.magicpacket.service.RedisService;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(String key, Object value, long expire, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, expire, timeUnit);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public Long incr(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    @Override
    public Long incr(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    @Override
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    @Override
    public Long getExpire(String key, TimeUnit timeUnit) {
        return redisTemplate.getExpire(key, timeUnit);
    }

    @Override
    public void add(String key, Object value) {
        redisTemplate.opsForSet().add(key, value);
    }

    @Override
    public Long size(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    @Override
    public boolean getBit(String key, long offset) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().getBit(key, offset));
    }

    @Override
    public List<Long> bitField(String key, BitFieldSubCommands subCommands) {
        return redisTemplate.opsForValue().bitField(key, subCommands);
    }

    @Override
    public void setBit(String key, long offset, boolean value) {
        redisTemplate.opsForValue().setBit(key, offset, value);
    }

    @Override
    public Set<String> keysForScan(String matchKey) {
        return redisTemplate.execute((RedisCallback<Set<String>>) connection -> {
            Set<String> keys = new HashSet<>();
            Cursor<byte[]> cursor = connection.scan(ScanOptions.scanOptions().match(matchKey + "*").build());
            while (cursor.hasNext()) {
                byte[] key = cursor.next();
                keys.add(new String(key));
            }
            return keys;
        });
    }

    @Override
    public void zAdd(String key, Object value, double score) {
        redisTemplate.opsForZSet().add(key, value, score);
    }

    @Override
    public Double zScore(String key, Object value) {
        return redisTemplate.opsForZSet().score(key, value);
    }

    @Override
    public void zRemove(String key, Object value) {
        redisTemplate.opsForZSet().remove(key, value);
    }

    @Override
    public Long zSize(String key) {
        return redisTemplate.opsForZSet().zCard(key);
    }

    @Override
    public Cursor<ZSetOperations.TypedTuple<Object>> getCursorForZSet(String key, ScanOptions options) {
        return redisTemplate.opsForZSet().scan(key, options);
    }
}
