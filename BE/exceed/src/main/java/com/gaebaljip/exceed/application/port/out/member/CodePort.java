package com.gaebaljip.exceed.application.port.out.member;

import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public interface CodePort {
    void saveWithExpiration(String key, String code, Long expiredTime);

    Optional<String> query(String key);

    void delete(String key);
}
