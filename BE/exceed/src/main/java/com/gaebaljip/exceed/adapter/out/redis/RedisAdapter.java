package com.gaebaljip.exceed.adapter.out.redis;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.application.port.out.member.CodePort;
import com.gaebaljip.exceed.common.annotation.Timer;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisAdapter implements CodePort {

    private final RedisUtils redisUtils;

    @Override
    public void saveWithExpiration(String key, String code, Long expiredTime) {
        redisUtils.setData(key, code, expiredTime);
    }

    @Override
    @Timer
    public Optional<String> query(String key) {
        return Optional.ofNullable(redisUtils.getData(key));
    }

    @Override
    public void delete(String key) {
        redisUtils.deleteData(key);
    }
}
