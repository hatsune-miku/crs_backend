package com.mikutart.crs_backend.v1.helper;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

public class RedisHelper {
    private final StringRedisTemplate redisTemplate;

    public RedisHelper(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public ValueOperations<String, String> redisOperation() {
        return redisTemplate.opsForValue();
    }

    public String get(String key) {
        return redisOperation().get(key);
    }

    public void set(String key, String value) {
        redisOperation().set(key, value);
    }
}
