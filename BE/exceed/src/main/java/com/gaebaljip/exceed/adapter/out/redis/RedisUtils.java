package com.gaebaljip.exceed.adapter.out.redis;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RedisUtils {

    private final RedisTemplate<String, Object> redisTemplate;

    public void setData(String key, String value, Long expiredTime) {
        redisTemplate.opsForValue().set(key, value, expiredTime, TimeUnit.MILLISECONDS);
    }

    public String getData(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    public void deleteData(String key) {
        redisTemplate.delete(key);
    }

    public Set<String> scanKeys(String pattern, long count) {
        Set<String> keys = new HashSet<>();
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        ScanOptions options = ScanOptions.scanOptions().match(pattern).count(count).build();
        Cursor<byte[]> cursor = connection.scan(options);
        cursor.forEachRemaining(key -> keys.add(new String(key)));
        return keys;
    }

    public void deleteDateWithPipeline(Set<String> keys) {
        Set<byte[]> keyBytes =
                keys.stream()
                        .map(key -> key.getBytes(StandardCharsets.UTF_8)) // UTF-8로 문자열을 바이트 배열로 변환
                        .collect(Collectors.toSet());
        redisTemplate.executePipelined(
                (RedisCallback<Object>)
                        connection -> {
                            keyBytes.stream().forEach(key -> connection.del(key));
                            return null;
                        });
    }

    public Boolean zAdd(String key, Object value, Double score) {
        return redisTemplate.opsForZSet().add(key, value, score);
    }

    public Boolean zAddIfAbsent(String key, Object value, Double score) {
        return redisTemplate.opsForZSet().addIfAbsent(key, value, score);
    }

    public Set<Object> zRange(String key, Long startRank, Long endRank) {
        return redisTemplate.opsForZSet().range(key, startRank, endRank);
    }

    public Long zRank(String key, Object value) {
        return redisTemplate.opsForZSet().rank(key, value);
    }

    public Long zSize(String key) {
        return redisTemplate.opsForZSet().size(key);
    }

    public Long sAdd(String key, Object value) {
        return redisTemplate.opsForSet().add(key, value);
    }

    public Long sRem(String key) {
        return redisTemplate.opsForSet().remove(key);
    }

    public Long sCard(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    public String flushDB() {
        return redisTemplate.execute(
                (RedisCallback<String>)
                        connection -> {
                            connection.flushDb();
                            return "ok";
                        });
    }
}
