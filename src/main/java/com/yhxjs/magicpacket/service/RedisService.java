package com.yhxjs.magicpacket.service;

import java.util.concurrent.TimeUnit;

public interface RedisService {

    Object get(String key);

    void set(String key, Object value, long expire, TimeUnit timeUnit);

    void delete(String key);
}
