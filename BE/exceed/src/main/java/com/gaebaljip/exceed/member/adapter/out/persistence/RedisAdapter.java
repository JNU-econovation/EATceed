package com.gaebaljip.exceed.member.adapter.out.persistence;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.common.RedisUtils;
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
    public Optional<String> query(String email) {
        return Optional.of(redisUtils.getData(email));
    }
}
