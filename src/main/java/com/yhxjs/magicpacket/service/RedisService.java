package com.yhxjs.magicpacket.service;


import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface RedisService {

    Object get(String key);

    void set(String key, Object value);

    void set(String key, Object value, long expire, TimeUnit timeUnit);

    void delete(String key);

    Long incr(String key);

    Long incr(String key, long delta);

    boolean hasKey(String key);

    Long getExpire(String key, TimeUnit timeUnit);

    void add(String key, Object value);

    Long size(String key);

    boolean getBit(String key, long offset);

    List<Long> bitField(String key, BitFieldSubCommands subCommands);

    void setBit(String key, long offset, boolean value);

    Set<String> keysForScan(String matchKey);

    void zAdd(String key, Object value, double score);

    Double zScore(String key, Object value);

    void zRemove(String key, Object value);

    Long zSize(String key);

    Cursor<ZSetOperations.TypedTuple<Object>> getCursorForZSet(String key, ScanOptions options);
}
