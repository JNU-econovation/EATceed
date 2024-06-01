package com.gaebaljip.exceed.member.adapter.out.persistence;

import java.util.Optional;

import com.gaebaljip.exceed.common.annotation.Timer;
import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.common.redis.RedisUtils;
import com.gaebaljip.exceed.member.application.port.out.TimeOutPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisAdapter implements TimeOutPort {

    private final RedisUtils redisUtils;

    private Long expiredTime = 600000L;

    @Override
    public void command(String email, String code) {
        redisUtils.setData(email, code, expiredTime);
    }

    @Override
    @Timer
    public Optional<String> query(String email) {
        return Optional.of(redisUtils.getData(email));
    }
}
